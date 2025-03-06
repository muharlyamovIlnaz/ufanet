package ru.ufanet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ufanet.dto.PoolReservationServiceResponse;
import ru.ufanet.dto.SignInRequest;
import ru.ufanet.dto.SignUpRequest;


@RequestMapping("/pool/auth")
@Tag(name = "Авторизация и аутентификация")
public interface AuthApi {

    @Operation(
            summary = "Регистрация пользователя",
            description = "Создание нового пользователя"
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
    @PostMapping("/sign-up")
    PoolReservationServiceResponse<String> signUp(@RequestBody @Valid SignUpRequest request);


    @Operation(
            summary = "Авторизация пользователя",
            description = "Получение токена"
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
    @PostMapping("/sign-in")
    PoolReservationServiceResponse<String> signIn(@RequestBody @Valid SignInRequest request);


}
