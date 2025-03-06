package ru.ufanet.dto;

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

    @Schema(description = "Имя пользователя", example = "Игорь")
    @Size(min = 3, max = 15, message = "Длина имени пользователя должна быть не менее 3 и не более 15 символов")
    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String name;

    @Schema(description = "Номер телефона", example = "89586234204")
    @Size(min = 11, max = 11, message = "Некорректный формат номера телефона")
    @NotBlank(message = "Номер телефона должен состоять из одинадцати чисел")
    private String phone;
}