package ru.ufanet.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.ufanet.controller.TimetableApi;
import ru.ufanet.dto.PoolReservationServiceResponse;
import ru.ufanet.dto.SlotRequest;
import ru.ufanet.dto.SlotResponse;
import ru.ufanet.service.TimetableService;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TimetableController implements TimetableApi {

    private final TimetableService timetableService;


    @Override
    public PoolReservationServiceResponse<SlotResponse> getBookedSlots(SlotRequest slotRequest) {
        return timetableService.getBookedSlots(slotRequest);
    }

    @Override
    public PoolReservationServiceResponse<SlotResponse> getAvailableSlots(SlotRequest slotRequest) {
        return timetableService.getAvailableSlots(slotRequest);
    }

    @Override
    public PoolReservationServiceResponse<SlotResponse> createSlot(SlotRequest slotRequest) {
        return timetableService.createSlot(slotRequest);
    }

    @Override
    public PoolReservationServiceResponse<String> deleteSlot(SlotRequest slotRequest) {
        return timetableService.deleteSlot(slotRequest);
    }
}
