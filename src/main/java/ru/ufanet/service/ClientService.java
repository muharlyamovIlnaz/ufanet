package ru.ufanet.service;

import ru.ufanet.dto.ClientCreateRequest;
import ru.ufanet.dto.ClientResponse;
import ru.ufanet.dto.ClientUpdateRequest;
import ru.ufanet.dto.PoolReservationServiceResponse;

import java.util.List;

public interface ClientService {

    PoolReservationServiceResponse<List<ClientResponse>> getClients();

    PoolReservationServiceResponse<ClientResponse> getClient(Long id);

    PoolReservationServiceResponse<String> addClient(ClientCreateRequest clientCreateRequest);

    PoolReservationServiceResponse<ClientResponse> updateClient(ClientUpdateRequest clientRequest);
}
