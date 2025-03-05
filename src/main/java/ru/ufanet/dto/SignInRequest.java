package com.effective.mobile.tskmngmntsystm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на аутентификацию")
public class SignInRequest {

    @Schema(description = "Электронный адрес", example = "ilnaz@mail.ru")
    @Size(min = 6, max = 50, message = "Электронный адрес должен содержать от 6 до 50 символов")
    @NotBlank(message = "Электронный адрес не может быть пустыми")
    @Email
    private String email;

    @Schema(description = "Пароль", example = "password")
    @Size(min = 8, max = 255, message = "Длина пароля должна быть от 8 до 255 символов")
    @NotBlank(message = "Пароль не может быть пустыми")
    private String password;
}