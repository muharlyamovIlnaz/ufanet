package ru.ufanet.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ufanet.dto.ClientCreateRequest;
import ru.ufanet.dto.ClientResponse;
import ru.ufanet.dto.ClientUpdateRequest;
import ru.ufanet.dto.PoolReservationServiceResponse;
import ru.ufanet.enums.Role;
import ru.ufanet.mapper.UserMapper;
import ru.ufanet.models.UserEntity;
import ru.ufanet.repository.UserRepository;
import ru.ufanet.service.ClientService;
import ru.ufanet.util.CurrentUserUtil;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class ClientServiceImpl implements ClientService {

    private final UserRepository userRepository;
    private final CurrentUserUtil currentUserUtil;
    private final UserMapper userMapper;


    @Override
    public PoolReservationServiceResponse<List<ClientResponse>> getClients() {
        try {
            if (hasAccess(null)) {
                log.info("Админ, с id = {} запрашивает список всех клиентов", currentUserUtil.getCurrentUserId());

                List<ClientResponse> allClientResponse = userRepository.findAll().stream()
                        .map(userMapper::toClientResponse).toList();
                log.info("Клиенты найдены успешно. Количество клиентов: {}", allClientResponse.size());
                return PoolReservationServiceResponse.ok("Список всех клиентов: ", allClientResponse);
            } else {
                log.warn("Пользователь, с id = {} без доступа запрашивает список всех клиентов", currentUserUtil.getCurrentUserId());
                return PoolReservationServiceResponse.ok("У вас нет доступа для получения этой информации");
            }
        } catch (Exception e) {
            log.error("Ошибка при получении списка клиентов", e);
            return PoolReservationServiceResponse.notOk("Произошла ошибка при запросе данных", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public PoolReservationServiceResponse<ClientResponse> getClient(Long id) {
        try {
            if (hasAccess(id)) {
                log.info("Пользователь с id = {} запрашивает информацию о клиенте с id = {}", currentUserUtil.getCurrentUserId(), id);
                UserEntity userEntity = userRepository.findById(id)
                        .orElse(null);
                if (userEntity == null) {
                    return PoolReservationServiceResponse.notOk("Клиент с id " + id + " не найден", HttpStatus.NOT_FOUND);
                }
                ClientResponse clientResponse = userMapper.toClientResponse(userEntity);
                log.info("Информация о клиенте с id = {} найдена успешно", id);
                return PoolReservationServiceResponse.ok("Информация о пользователе с id " + id, clientResponse);
            } else {
                log.warn("Пользователь с id = {} без доступа запрашивает информацию о клиенте с id = {}", currentUserUtil.getCurrentUserId(), id);
                return PoolReservationServiceResponse.ok("У вас нет доступа для получения этой информации");
            }
        } catch (Exception e) {
            log.error("Ошибка при получении информации о клиенте с id {}", id, e);
            return PoolReservationServiceResponse.notOk("Произошла ошибка при запросе данных", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public PoolReservationServiceResponse<String> addClient(ClientCreateRequest clientCreateRequest) {
        try {
            if (hasAccess(null)) {
                log.info("Админ, с id = {} хочет добавить нового клиента", currentUserUtil.getCurrentUserId());
                UserEntity userEntity = userMapper.toUserEntity(clientCreateRequest);
                userEntity.setPassword("defaultPassword"); // todo
                userRepository.save(userEntity);
                log.info("Новый клиент добавлен успешно");
                return PoolReservationServiceResponse.ok("Новый клиент добавлен");
            } else {
                log.warn("Пользователь с id = {} без доступа пытается добавить нового клиента", currentUserUtil.getCurrentUserId());
                return PoolReservationServiceResponse.ok("У вас нет доступа к этим действиям");
            }
        } catch (Exception e) {
            log.error("Ошибка при добавлении нового клиента", e);
            return PoolReservationServiceResponse.notOk("Произошла ошибка при добавлении клиента", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public PoolReservationServiceResponse<ClientResponse> updateClient(ClientUpdateRequest clientRequest) {
        log.info("Пользователь с id = {} хочет обновить данные клиента с id = {}", currentUserUtil.getCurrentUserId(), clientRequest.getId());
        try {
            if (hasAccess(clientRequest.getId())) {
                Optional<UserEntity> userEntityOpt = userRepository.findById(clientRequest.getId());
                if (userEntityOpt.isPresent()) {
                    UserEntity userEntity = userEntityOpt.get();

                    int updatedUserEntityInt = userRepository.updateUser(
                            clientRequest.getId(),
                            clientRequest.getName(),
                            clientRequest.getPhone(),
                            clientRequest.getEmail()
                    );
                    if (updatedUserEntityInt > 0) {
                        ClientResponse clientResponse = userMapper.toClientResponse(userEntity);
                        log.info("Данные клиента с id = {} успешно обновлены", clientRequest.getId());
                        return PoolReservationServiceResponse.ok("Данные клиента успешно обновлены", clientResponse);
                    } else {
                        log.error("Данные клиента с id = {} не были изменены", clientRequest.getId());
                        return PoolReservationServiceResponse.notOk("Данные не были изменены", HttpStatus.BAD_REQUEST);
                    }
                } else {
                    log.error("Клиент с id = {} не найден", clientRequest.getId());
                    return PoolReservationServiceResponse.notOk("Клиент не найден", HttpStatus.NOT_FOUND);
                }
            }
            log.warn("Пользователь с id = {} без доступа пытается обновить данные клиента с id = {}", currentUserUtil.getCurrentUserId(), clientRequest.getId());
            return PoolReservationServiceResponse.ok("У вас нет доступа к этой операции");
        } catch (Exception e) {
            log.error("Ошибка при обновлении данных клиента с id {}", clientRequest.getId(), e);
            return PoolReservationServiceResponse.notOk("Произошла ошибка при обновлении данных клиента", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean hasAccess(Long userId) {
        return currentUserUtil.getCurrentUserRole().equals(Role.ROLE_ADMIN)
                || currentUserUtil.getCurrentUserId().equals(userId);
    }
}
