package com.effective.mobile.tskmngmntsystm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на регистрацию")
public class SignUpRequest {

    @Schema(description = "Электронный адрес", example = "ilnaz@mail.ru")
    @Size(min = 6, max = 50, message = "Электронный адрес должен содержать от 6 до 50 символов")
    @NotBlank(message = "Электронный адрес не может быть пустыми")
    @Email
    private String email;

    @Schema(description = "Пароль", example = "password")
    @Size(min = 8, max = 255, message = "Длина пароля должна быть не менее 8 и не более 255 символов")
    @NotBlank(message = "Пароль пользователя не может быть пустыми")
    private String password;

    @Schema(description = "Роль пользователя", example = "ROLE_ADMIN")
    @Size(min = 3, max = 11, message = "ROLE_USER, ROLE_ADMIN")
    @NotBlank(message = "Роль пользователя не может быть пустыми")
    private String role;
}