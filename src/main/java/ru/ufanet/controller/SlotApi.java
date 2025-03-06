package ru.ufanet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ufanet.dto.ClientResponse;
import ru.ufanet.dto.SlotResponse;
import ru.ufanet.dto.PoolReservationServiceResponse;

import java.util.List;

@RequestMapping("/pool/timetable")
@Tag(name = "API для работы с записями")
public interface SlotApi {

    @Operation(summary = "Получение занятых записей на определенную дату",
            description = "Список записей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Ответ получен",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PoolReservationServiceResponse.class))
                    })
    })
    @GetMapping("/busy")
    PoolReservationServiceResponse<List<SlotResponse>> getBusySlots(@RequestParam String date);

    @Operation(summary = "Получение доступных записей на определенную дату",
            description = "Список записей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Ответ получен",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PoolReservationServiceResponse.class))
                    })
    })
    @GetMapping("/free")
    PoolReservationServiceResponse<List<SlotResponse>> getFreeSlots(@RequestParam String date);


    @Operation(summary = "Получение информации о клиентах на определенный слот",
            description = "Список записей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Ответ получен",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PoolReservationServiceResponse.class))
                    })
    })
    @GetMapping("/clientsInfo")
    PoolReservationServiceResponse<List<ClientResponse>> getClientsByTime(@RequestParam String datetime);


    @Operation(summary = "Записать клиента от имени Админа",
            description = "Добавление записи администратором")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Ответ получен",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PoolReservationServiceResponse.class))
                    })
    })
    @PostMapping("/admin/create")
    PoolReservationServiceResponse<SlotResponse> createSlotByAdmin(@RequestParam Long clientId, @RequestParam String datetime);

    @Operation(summary = "Записаться в бассейн",
            description = "Добавление записи клиентом")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Ответ получен",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PoolReservationServiceResponse.class))
                    })
    })
    @PostMapping("/self/create")
    PoolReservationServiceResponse<SlotResponse> createSlotByClient(@RequestParam String datetime);


    @Operation(summary = "Отмена записи клиента",
            description = "Отмена записи")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Ответ получен",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PoolReservationServiceResponse.class))
                    })
    })
    @DeleteMapping
    PoolReservationServiceResponse<String> deleteSlot(@RequestParam Long reservationId);


}
