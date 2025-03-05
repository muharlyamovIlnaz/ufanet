package ru.ufanet.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ufanet.dto.PoolReservationServiceResponse;
import ru.ufanet.dto.SlotRequest;
import ru.ufanet.dto.SlotResponse;
import ru.ufanet.service.TimetableService;


@RequiredArgsConstructor
@Slf4j
@Service
public class TimetableServiceImpl implements TimetableService {


    @Override
    public PoolReservationServiceResponse<SlotResponse> getBookedSlots(SlotRequest slotRequest) {
        return null;
    }

    @Override
    public PoolReservationServiceResponse<SlotResponse> getAvailableSlots(SlotRequest slotRequest) {
        return null;
    }

    @Override
    public PoolReservationServiceResponse<SlotResponse> createSlot(SlotRequest slotRequest) {
        return null;
    }

    @Override
    public PoolReservationServiceResponse<String> deleteSlot(SlotRequest slotRequest) {
        return null;
    }
}
