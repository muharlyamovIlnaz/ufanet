package com.effective.mobile.tskmngmntsystm.controller;

import com.effective.mobile.tskmngmntsystm.dto.SignInRequest;
import com.effective.mobile.tskmngmntsystm.dto.SignUpRequest;
import com.effective.mobile.tskmngmntsystm.dto.TaskServiceResponse;
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


@RequestMapping("/auth")
@Tag(name = "Аутентификация")
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
                                    schema = @Schema(implementation = TaskServiceResponse.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    }
            )
    })
    @PostMapping("/sign-up")
    TaskServiceResponse<String> signUp(@RequestBody @Valid SignUpRequest request);


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
                                    schema = @Schema(implementation = TaskServiceResponse.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    }
            )
    })
    @PostMapping("/sign-in")
    TaskServiceResponse<String> signIn(@RequestBody @Valid SignInRequest request);


}
