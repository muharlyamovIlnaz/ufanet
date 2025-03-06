package ru.ufanet.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ufanet.dto.ClientCreateRequest;
import ru.ufanet.dto.ClientResponse;
import ru.ufanet.dto.ClientUpdateRequest;
import ru.ufanet.dto.PoolReservationServiceResponse;
import ru.ufanet.enums.Role;
import ru.ufanet.mapper.UserMapper;
import ru.ufanet.models.UserEntity;
import ru.ufanet.repository.UserRepository;
import ru.ufanet.util.CurrentUserUtil;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CurrentUserUtil currentUserUtil;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    private UserEntity userEntity;
    private ClientResponse clientResponse;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("Иван");
        userEntity.setEmail("ivan@mail.ru");
        userEntity.setPhone("89276234204");
        userEntity.setRole(Role.ROLE_USER);

        clientResponse = new ClientResponse();
        clientResponse.setId(1L);
        clientResponse.setName("Иван");
        clientResponse.setEmail("ivan@mail.ru");
        clientResponse.setPhone("89276234204");
    }

    @Test
    void getClients_Success() {
        // given
        when(currentUserUtil.getCurrentUserRole()).thenReturn(Role.ROLE_ADMIN);
        when(userRepository.findAll()).thenReturn(List.of(userEntity));
        when(userMapper.toClientResponse(userEntity)).thenReturn(clientResponse);

        // when
        PoolReservationServiceResponse<List<ClientResponse>> response = clientService.getClients();

        // then
        assertEquals(200, response.getStatus());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Иван", response.getBody().get(0).getName());
    }

    @Test
    void getClient_Success() {
        // given
        when(currentUserUtil.getCurrentUserRole()).thenReturn(Role.ROLE_ADMIN);
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(userMapper.toClientResponse(userEntity)).thenReturn(clientResponse);

        // when
        PoolReservationServiceResponse<ClientResponse> response = clientService.getClient(1L);

        // then
        assertEquals(200, response.getStatus());
        assertEquals("Иван", response.getBody().getName());
    }

    @Test
    void getClient_NotFound() {
        // given
        when(currentUserUtil.getCurrentUserRole()).thenReturn(Role.ROLE_ADMIN);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // when
        PoolReservationServiceResponse<ClientResponse> response = clientService.getClient(1L);

        // then
        assertEquals(404, response.getStatus());
        assertNull(response.getBody());
    }

    @Test
    void addClient_Success() {
        // given
        when(currentUserUtil.getCurrentUserRole()).thenReturn(Role.ROLE_ADMIN);
        when(userMapper.toUserEntity(any(ClientCreateRequest.class))).thenReturn(userEntity);

        ClientCreateRequest request = new ClientCreateRequest();
        request.setName("Иван");
        request.setEmail("ivan@mail.ru");
        request.setPhone("89276234204");

        // when
        PoolReservationServiceResponse<String> response = clientService.addClient(request);

        // then
        assertEquals(200, response.getStatus());
        assertEquals("Новый клиент добавлен", response.getMessage());
    }

    @Test
    void updateClient_Success() {
        // given
        when(currentUserUtil.getCurrentUserRole()).thenReturn(Role.ROLE_ADMIN);
        when(currentUserUtil.getCurrentUserId()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(userRepository.updateUser(anyLong(), any(), any(), any())).thenReturn(1);
        when(userMapper.toClientResponse(userEntity)).thenReturn(clientResponse);

        ClientUpdateRequest request = new ClientUpdateRequest();
        request.setId(1L);
        request.setName("Петр");
        request.setEmail("petr@mail.ru");
        request.setPhone("89270000000");

        // when
        PoolReservationServiceResponse<ClientResponse> response = clientService.updateClient(request);

        // then
        assertEquals(200, response.getStatus());
        assertNotNull(response.getBody());
        assertEquals("Иван", response.getBody().getName());
    }

    @Test
    void updateClient_NotFound() {
        // given
        when(currentUserUtil.getCurrentUserRole()).thenReturn(Role.ROLE_ADMIN);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ClientUpdateRequest request = new ClientUpdateRequest();
        request.setId(1L);

        // when
        PoolReservationServiceResponse<ClientResponse> response = clientService.updateClient(request);

        // then
        assertEquals(404, response.getStatus());
        assertNull(response.getBody());
    }
}