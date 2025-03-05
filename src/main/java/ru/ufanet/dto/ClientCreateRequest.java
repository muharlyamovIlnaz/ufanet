package ru.ufanet.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Сущность клиента")
public class ClientRequest {

    @Schema(description = "Идентификатор клиента", example = "1")
    private Long id;

    @Schema(description = "Имя клиента", example = "Иван")
    private String name;

    @Schema(description = "Номер телефона клиента", example = "89276234204")
    private String phone;

    @Schema(description = "Электронная почта клиента", example = "example@mail.ru")
    private String email;
}
