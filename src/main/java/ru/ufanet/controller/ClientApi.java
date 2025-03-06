package ru.ufanet.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ufanet.dto.ClientCreateRequest;
import ru.ufanet.dto.ClientResponse;
import ru.ufanet.dto.ClientUpdateRequest;
import ru.ufanet.dto.PoolReservationServiceResponse;

import java.util.List;

@RequestMapping("/pool/client")
@Tag(name = "API для работы с клиентами")
public interface ClientApi {


    @Operation(
            summary = "Получение списка всех клиентов",
            description = "Список клиентов"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ответ получен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PoolReservationServiceResponse.class)
                            )
                    }
            )
    })
    @GetMapping
    PoolReservationServiceResponse<List<ClientResponse>> getClients();

    @Operation(
            summary = "Получение данных клиента по id",
            description = "Данные клиента"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ответ получен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PoolReservationServiceResponse.class)
                            )
                    }
            )
    })
    @GetMapping("/{id}")
    PoolReservationServiceResponse<ClientResponse> getClient(@PathVariable(name = "id") Long id);


    @Operation(
            summary = "Добавление нового клиента",
            description = "Данные клиента"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ответ получен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PoolReservationServiceResponse.class)
                            )
                    }
            )
    })
    @PostMapping
    PoolReservationServiceResponse<String> addClient(ClientCreateRequest clientCreateRequest);


    @Operation(
            summary = "Обновление данных клиента",
            description = "Измененные данные"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ответ получен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PoolReservationServiceResponse.class)
                            )
                    }
            )
    })
    @PutMapping
    PoolReservationServiceResponse<ClientResponse> updateClient(ClientUpdateRequest clientRequest);
}
