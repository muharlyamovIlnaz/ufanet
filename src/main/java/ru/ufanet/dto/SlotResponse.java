package ru.ufanet.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


@Data
@NoArgsConstructor
@Schema(description = "Сущность записи")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SlotResponse {

    public SlotResponse(LocalTime time, Integer count) {
        this.time = time;
        this.count = count;
    }

    @Schema(description = "Идентификатор записи", example = "1")
    private Long id;

    @Schema(description = "Дата записи", example = "2025-02-03")
    private LocalDate data;

    @Schema(description = "Время записи", example = "10:00")
    private LocalTime time;

    @Schema(description = "Идентификатор клиента", example = "1")
    private Long clientId;

    @Schema(description = "Количество записей на это время", example = "10")
    private Integer count;
}
