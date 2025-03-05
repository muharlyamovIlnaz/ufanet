package ru.ufanet.service;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.ufanet.dto.PoolReservationServiceResponse;
import ru.ufanet.dto.SlotRequest;
import ru.ufanet.dto.SlotResponse;

public interface TimetableService {

    PoolReservationServiceResponse<SlotResponse> getBookedSlots(SlotRequest slotRequest);

    PoolReservationServiceResponse<SlotResponse> getAvailableSlots(SlotRequest slotRequest);

    PoolReservationServiceResponse<SlotResponse> createSlot(SlotRequest slotRequest);

    PoolReservationServiceResponse<String> deleteSlot(SlotRequest slotRequest);
}
