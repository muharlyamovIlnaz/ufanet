package ru.ufanet.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.ufanet.dto.ClientResponse;
import ru.ufanet.dto.PoolReservationServiceResponse;
import ru.ufanet.dto.SlotResponse;
import ru.ufanet.enums.Role;
import ru.ufanet.exception.CustomException;
import ru.ufanet.mapper.SlotMapper;
import ru.ufanet.mapper.UserMapper;
import ru.ufanet.models.SlotEntity;
import ru.ufanet.models.UserEntity;
import ru.ufanet.repository.SlotRepository;
import ru.ufanet.repository.UserRepository;
import ru.ufanet.service.SlotService;
import ru.ufanet.util.CurrentUserUtil;
import ru.ufanet.util.CurrentYearHolidays;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Slf4j
@Service
public class SlotServiceImpl implements SlotService {

    private final SlotRepository slotRepository;
    private final UserRepository userRepository;
    private final SlotMapper slotMapper;
    private final UserMapper userMapper;
    private final CurrentUserUtil currentUserUtil;
    private final CurrentYearHolidays currentYearHolidays;


    private static final int MAX_SLOTS_PER_HOUR = 10;
    private static final LocalTime START_TIME = LocalTime.of(9, 0);
    private static final LocalTime END_TIME = LocalTime.of(21, 0);


    @Override
    public PoolReservationServiceResponse<List<SlotResponse>> getBusySlots(LocalDate date) {
        try {
            log.info("Получение занятых записей на определенную дату. Date: {}", date);
            List<SlotResponse> responseList = slotRepository.getBusySlotsCount(date).stream()
                    .map(obj -> new SlotResponse(
                            ((java.sql.Time) obj[0]).toLocalTime(),
                            ((Number) obj[1]).intValue()
                    ))
                    .collect(Collectors.toList());

            return PoolReservationServiceResponse.ok("Занятые записи получены", responseList);
        } catch (Exception e) {
            log.error("Ошибка при получении занятых записей на определенную дату. Error: {}", e.getMessage());
            throw new CustomException("Ошибка при получении занятых записей на определенную дату", e);
        }
    }

    @Override
    public PoolReservationServiceResponse<List<SlotResponse>> getFreeSlots(LocalDate date) {
        log.info("Получение доступных записей на определенную дату. Date: {}", date);
        List<SlotResponse> availableSlots = new ArrayList<>();

        LocalTime currentTime = START_TIME;
        try {
            while (!currentTime.isAfter(END_TIME)) {
                int bookedCount = slotRepository.countByDataAndTime(date, currentTime);
                int availableCount = MAX_SLOTS_PER_HOUR - bookedCount;

                if (availableCount > 0) {
                    availableSlots.add(new SlotResponse(currentTime, availableCount));
                }
                currentTime = currentTime.plusHours(1);
            }
            return PoolReservationServiceResponse.ok("Доступные записи получены", availableSlots);
        } catch (Exception e) {
            log.error("Ошибка при получении доступных записей на определенную дату. Error: {}", e.getMessage());
            throw new CustomException("Ошибка при получении доступных записей на определенную дату", e);
        }
    }

    @Override
    public PoolReservationServiceResponse<List<ClientResponse>> getClientsByTime(LocalDateTime dateTime) {
        try {
            log.info("Получение списка клиентов на определенную дату и время. DateTime: {}", dateTime);
            if (!(currentUserUtil.getCurrentUserRole().equals(Role.ROLE_ADMIN))) {
                return PoolReservationServiceResponse.notOk("Нет доступа", HttpStatus.UNAUTHORIZED);
            }
            List<Long> clientIds = slotRepository.getClientsIdByDataAndTime(dateTime.toLocalDate(), dateTime.toLocalTime());
            List<UserEntity> allUserById = userRepository.findByIdIn(clientIds);
            List<ClientResponse> allUsersById = allUserById.stream()
                    .map(userMapper::toClientResponse)
                    .collect(Collectors.toList());
            return PoolReservationServiceResponse.ok("Список клиентов на " + dateTime, allUsersById);
        } catch (Exception e) {
            log.error("Ошибка при получении списка клиентов на определенную дату и время. Error: {}", e.getMessage());
            throw new CustomException("Ошибка при получении списка клиентов на определенную дату и время", e);
        }
    }


    @Override
    public PoolReservationServiceResponse<SlotResponse> createSlotByAdmin(Long clientId, LocalDateTime dateTime) {
        log.info("Создание записи админом для клиента с id {} на дату и время {}", clientId, dateTime);
        if (!currentUserUtil.getCurrentUserRole().equals(Role.ROLE_ADMIN)) {
            return PoolReservationServiceResponse.notOk("Нет доступа", HttpStatus.UNAUTHORIZED);
        }
        return createSlot(clientId, dateTime);
    }

    @Override
    public PoolReservationServiceResponse<SlotResponse> createSlotByClient(LocalDateTime dateTime) {
        log.info("Создание записи клиентом с id {} на дату и время {}", currentUserUtil.getCurrentUserId(), dateTime);
        Long clientId = currentUserUtil.getCurrentUserId();
        return createSlot(clientId, dateTime);
    }


    @Override
    public PoolReservationServiceResponse<String> deleteSlot(Long slotId) {
        try {
            log.info("Удаление записи с id {}", slotId);
            SlotEntity slotEntity = slotRepository.findById(slotId).orElseThrow(() -> new EntityNotFoundException("Запись не найдена"));
            if (currentUserUtil.getCurrentUserRole().equals(Role.ROLE_ADMIN) || currentUserUtil.getCurrentUserId().equals(slotEntity.getClientId())) {
                slotRepository.delete(slotEntity);
                return PoolReservationServiceResponse.ok("Запись удалена");
            } else {
                return PoolReservationServiceResponse.notOk("Нет доступа", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.error("Ошибка при удалении записи. Error: {}", e.getMessage());
            throw new CustomException("Ошибка при удалении записи", e);
        }
    }


    private PoolReservationServiceResponse<SlotResponse> createSlot(Long clientId, LocalDateTime dateTime) {
        try {
            LocalDate data = dateTime.toLocalDate();
            LocalTime time = dateTime.toLocalTime();

            if (currentYearHolidays.isHoliday(data)) {
                return PoolReservationServiceResponse.notOk(
                        "Бассейн не работает в этот день: " + currentYearHolidays.getHolidayName(data),
                        HttpStatus.BAD_REQUEST);
            }
            if (data.getDayOfWeek().equals(DayOfWeek.SATURDAY) || data.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                log.info("Клиент выбрал выходной день или праздничный день");
                return PoolReservationServiceResponse.notOk("Бассейн не работает в выходные дни", HttpStatus.BAD_REQUEST);
            }
            if (time.getMinute() != 0) {
                log.info("Клиент выбрал некорректное время");
                return PoolReservationServiceResponse.notOk("Некорректный формат записи, время должно быть кратно одному часу", HttpStatus.BAD_REQUEST);
            }
            if (time.isBefore(START_TIME) || time.isAfter(END_TIME)) {
                log.info("Клиент выбрал нерабочее время");
                return PoolReservationServiceResponse.notOk("Бассейн работает с 9:00 до 22:00", HttpStatus.BAD_REQUEST);
            }
            if (slotRepository.countByDataAndTime(data, time) >= MAX_SLOTS_PER_HOUR) {
                log.info("Бассейн полон");
                return PoolReservationServiceResponse.notOk("В это время бассейн полон", HttpStatus.NOT_FOUND);
            }
            List<Long> clientsId = slotRepository.getClientIdByData(data);
            if (clientsId.contains(clientId)) {
                log.info("Клиент уже записан на этот день");
                return PoolReservationServiceResponse.notOk("Вы уже записаны на этот день", HttpStatus.NOT_FOUND);
            }
            if (userRepository.findById(clientId).isEmpty()) {
                log.info("Пользователя с таким id = {} не существует", clientId);
                return PoolReservationServiceResponse.notOk("Пользователя с таким id не существует", HttpStatus.NOT_FOUND);
            }

            SlotEntity slotEntity = new SlotEntity();
            slotEntity.setClientId(clientId);
            slotEntity.setData(data);
            slotEntity.setTime(time);

            SlotEntity savedSlot = slotRepository.save(slotEntity);
            SlotResponse slotResponse = slotMapper.toSlotResponse(savedSlot);
            log.info("Клиент {} записан на слот {}", clientId, savedSlot);
            return PoolReservationServiceResponse.ok("Клиент записан на слот", slotResponse);
        } catch (Exception e) {
            log.error("Ошибка при добавлении записи. Error: {}", e.getMessage());
            throw new CustomException("Ошибка при добавлении записи", e);
        }
    }

}
