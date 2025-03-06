package ru.ufanet.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.ufanet.controller.SlotApi;
import ru.ufanet.dto.ClientResponse;
import ru.ufanet.dto.PoolReservationServiceResponse;
import ru.ufanet.dto.SlotResponse;
import ru.ufanet.exception.CustomException;
import ru.ufanet.service.SlotService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SlotController implements SlotApi {

    private final SlotService timetableService;


    @Override
    public PoolReservationServiceResponse<List<SlotResponse>> getBusySlots(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate dateTime = LocalDate.parse(date, formatter);
            return timetableService.getBusySlots(dateTime);
        } catch (Exception e) {
            throw new CustomException("Некорректный формат даты, формат даты должен быть yyyy-MM-dd", e);
        }


    }

    @Override
    public PoolReservationServiceResponse<List<SlotResponse>> getFreeSlots(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate dateTime = LocalDate.parse(date, formatter);
            return timetableService.getFreeSlots(dateTime);
        } catch (Exception e) {
            throw new CustomException("Некорректный формат даты, формат даты должен быть yyyy-MM-dd", e);
        }
    }

    @Override
    public PoolReservationServiceResponse<List<ClientResponse>> getClientsByTime(String datetime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
        try {
            LocalDateTime dateTime = LocalDateTime.parse(datetime, formatter);
            return timetableService.getClientsByTime(dateTime);
        } catch (Exception e) {
            throw new CustomException("Некорректный формат даты, формат даты должен быть yyyy-MM-dd-HH:mm", e);
        }
    }

    @Override
    public PoolReservationServiceResponse<SlotResponse> createSlotByAdmin(Long clientId, String datetime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
        try {
            LocalDateTime dateTime = LocalDateTime.parse(datetime, formatter);
            return timetableService.createSlotByAdmin(clientId, dateTime);
        } catch (Exception e) {
            throw new CustomException("Некорректный формат даты, формат даты должен быть yyyy-MM-dd-HH:mm", e);
        }
    }

    @Override
    public PoolReservationServiceResponse<SlotResponse> createSlotByClient(String datetime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
        try {
            LocalDateTime dateTime = LocalDateTime.parse(datetime, formatter);
            return timetableService.createSlotByClient(dateTime);
        } catch (Exception e) {
            throw new CustomException("Некорректный формат даты, формат даты должен быть yyyy-MM-dd-HH:mm", e);
        }
    }

    @Override
    public PoolReservationServiceResponse<String> deleteSlot(Long reservationId) {
        return timetableService.deleteSlot(reservationId);
    }
}
