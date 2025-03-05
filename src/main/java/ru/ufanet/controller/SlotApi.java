package ru.ufanet.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ufanet.dto.SlotRequest;
import ru.ufanet.dto.SlotResponse;
import ru.ufanet.dto.PoolReservationServiceResponse;

@RequestMapping("/pool/timetable")
@Tag(name = "API для работы с записями")
public interface TimetableApi {

    @GetMapping("/booked")
    PoolReservationServiceResponse<SlotResponse> getBookedSlots(SlotRequest slotRequest);

    @GetMapping("/available")
    PoolReservationServiceResponse<SlotResponse> getAvailableSlots(SlotRequest slotRequest);

    @PostMapping
    PoolReservationServiceResponse<SlotResponse> createSlot(SlotRequest slotRequest);

    @DeleteMapping
    PoolReservationServiceResponse<String> deleteSlot(SlotRequest slotRequest);

}
