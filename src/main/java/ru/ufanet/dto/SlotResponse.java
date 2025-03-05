package ru.ufanet.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ufanet.enums.Time;

import java.util.Calendar;


@Data
@NoArgsConstructor
@Schema(description = "Сущность записи")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {

    @Schema(description = "Идентификатор записи", example = "1")
    private Long orderId;

    @Schema(description = "Дата записи", example = "11-02-2025")
    private Calendar data;

    @Schema(description = "Время записи", example = "TIME_10")
    private Time time;

    @Schema(description = "Колличество записей на это время", example = "10")
    private Integer count;
}
