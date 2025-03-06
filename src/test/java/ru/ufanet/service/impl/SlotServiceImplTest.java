package ru.ufanet.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import ru.ufanet.dto.PoolReservationServiceResponse;
import ru.ufanet.dto.SlotResponse;
import ru.ufanet.enums.Role;
import ru.ufanet.mapper.SlotMapper;
import ru.ufanet.mapper.UserMapper;
import ru.ufanet.models.UserEntity;
import ru.ufanet.repository.SlotRepository;
import ru.ufanet.repository.UserRepository;
import ru.ufanet.util.CurrentUserUtil;
import ru.ufanet.util.CurrentYearHolidays;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SlotServiceImplTest {

    private SlotServiceImpl slotService;

    @Mock
    private SlotRepository slotRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SlotMapper slotMapper;

    @Mock
    private UserMapper userMapper;

    @Mock
    private CurrentUserUtil currentUserUtil;

    @Mock
    private CurrentYearHolidays currentYearHolidays;

    @BeforeEach
    void init() {
        slotService = new SlotServiceImpl(slotRepository, userRepository, slotMapper, userMapper, currentUserUtil, currentYearHolidays);
    }

    @Test
    void createSlotByAdmin() {
        //given
        Long id = 1L;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("2007-12-03-10:00", formatter);
        when(userRepository.findById(id)).thenReturn(Optional.of(new UserEntity()));
        when(currentUserUtil.getCurrentUserRole()).thenReturn(Role.ROLE_ADMIN);

        //when
        PoolReservationServiceResponse<SlotResponse> slotByAdmin = slotService.createSlotByAdmin(id, dateTime);

        //then
        assertEquals("Клиент записан на слот", slotByAdmin.getMessage());
    }

    @Test
    void createSlotByAdmin_UserNotFound() {
        // given
        Long id = 1L;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("2007-12-03-10:00", formatter);

        when(userRepository.findById(id)).thenReturn(Optional.empty());
        when(currentUserUtil.getCurrentUserRole()).thenReturn(Role.ROLE_ADMIN);

        // when
        PoolReservationServiceResponse<SlotResponse> response = slotService.createSlotByAdmin(id, dateTime);

        // then
        assertEquals("Пользователя с таким id не существует", response.getMessage());
        assertEquals(404, response.getStatus());
    }

    @Test
    void createSlotByClient() {
        //given
        when(currentUserUtil.getCurrentUserId()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(new UserEntity()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("2007-12-03-10:00", formatter);

        //when
        PoolReservationServiceResponse<SlotResponse> response = slotService.createSlotByClient(dateTime);

        //then
        assertEquals("Клиент записан на слот", response.getMessage());
        assertEquals(200, response.getStatus());
    }

    @Test
    void createSlotByClient_WhenHoliday() {
        //given
        when(currentUserUtil.getCurrentUserId()).thenReturn(1L);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("2025-11-04-10:00", formatter);
        when(currentYearHolidays.isHoliday(dateTime.toLocalDate())).thenReturn(true);
        when(currentYearHolidays.getHolidayName(dateTime.toLocalDate())).thenReturn("День народного единства");

        //when
        PoolReservationServiceResponse<SlotResponse> response = slotService.createSlotByClient(dateTime);

        //then
        assertEquals("Бассейн не работает в этот день: День народного единства", response.getMessage());
        assertEquals(400, response.getStatus());
    }


    @Test
    void createSlotByClient_WhenAfterClosingTime() {
        //given
        when(currentUserUtil.getCurrentUserId()).thenReturn(1L);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("2025-09-02-23:00", formatter);
        when(currentYearHolidays.isHoliday(dateTime.toLocalDate())).thenReturn(false);

        //when
        PoolReservationServiceResponse<SlotResponse> response = slotService.createSlotByClient(dateTime);

        //then
        assertEquals("Бассейн работает с 9:00 до 22:00", response.getMessage());
        assertEquals(400, response.getStatus());
    }


}

