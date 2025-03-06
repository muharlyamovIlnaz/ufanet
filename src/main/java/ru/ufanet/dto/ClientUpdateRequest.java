package ru.ufanet.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Сущность клиента для обновления")
public class ClientUpdateRequest extends ClientCreateRequest{

    @Schema(description = "Идентификатор клиента", example = "1")
    private Long id;

}
