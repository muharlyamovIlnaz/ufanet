package ru.ufanet.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.ufanet.controller.ClientApi;
import ru.ufanet.dto.ClientCreateRequest;
import ru.ufanet.dto.ClientResponse;
import ru.ufanet.dto.ClientUpdateRequest;
import ru.ufanet.dto.PoolReservationServiceResponse;
import ru.ufanet.service.ClientService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ClientController implements ClientApi {

    private final ClientService clientService;

    @Override
    public PoolReservationServiceResponse<List<ClientResponse>> getClients() {
        return clientService.getClients();
    }

    @Override
    public PoolReservationServiceResponse<ClientResponse> getClient(Long id) {
        return clientService.getClient(id);
    }

    @Override
    public PoolReservationServiceResponse<String> addClient(ClientCreateRequest clientCreateRequest) {
        return clientService.addClient(clientCreateRequest);
    }

    @Override
    public PoolReservationServiceResponse<ClientResponse> updateClient(ClientUpdateRequest clientRequest) {
        return clientService.updateClient(clientRequest);
    }
}
