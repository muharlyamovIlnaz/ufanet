package ru.ufanet.service;

import ru.ufanet.dto.ClientResponse;
import ru.ufanet.dto.PoolReservationServiceResponse;
import ru.ufanet.dto.SlotResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SlotService {

    PoolReservationServiceResponse<List<SlotResponse>> getBusySlots(LocalDate data);

    PoolReservationServiceResponse<List<SlotResponse>> getFreeSlots(LocalDate data);

    PoolReservationServiceResponse<List<ClientResponse>> getClientsByTime(LocalDateTime dateTime);

    PoolReservationServiceResponse<SlotResponse> createSlotByAdmin(Long clientId, LocalDateTime dateTime);

    PoolReservationServiceResponse<SlotResponse> createSlotByClient(LocalDateTime dateTime);

    PoolReservationServiceResponse<String> deleteSlot(Long slotId);


}
