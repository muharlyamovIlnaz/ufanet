package ru.ufanet.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class CurrentYearHolidays {

    private final Map<LocalDate, String> HOLIDAYS_2025 = new HashMap<>();

    public CurrentYearHolidays() {
        HOLIDAYS_2025.put(LocalDate.of(2025, 1, 1), "Новогодние каникулы");
        HOLIDAYS_2025.put(LocalDate.of(2025, 1, 2), "Новогодние каникулы");
        HOLIDAYS_2025.put(LocalDate.of(2025, 1, 3), "Новогодние каникулы");
        HOLIDAYS_2025.put(LocalDate.of(2025, 1, 4), "Новогодние каникулы");
        HOLIDAYS_2025.put(LocalDate.of(2025, 1, 5), "Новогодние каникулы");
        HOLIDAYS_2025.put(LocalDate.of(2025, 1, 6), "Новогодние каникулы");
        HOLIDAYS_2025.put(LocalDate.of(2025, 1, 7), "Рождество Христово");
        HOLIDAYS_2025.put(LocalDate.of(2025, 1, 8), "Новогодние каникулы");
        HOLIDAYS_2025.put(LocalDate.of(2025, 2, 23), "День защитника Отечества");
        HOLIDAYS_2025.put(LocalDate.of(2025, 3, 8), "Международный женский день");
        HOLIDAYS_2025.put(LocalDate.of(2025, 5, 1), "Праздник Весны и Труда");
        HOLIDAYS_2025.put(LocalDate.of(2025, 5, 2), "Праздник Весны и Труда");
        HOLIDAYS_2025.put(LocalDate.of(2025, 5, 8), "День Победы");
        HOLIDAYS_2025.put(LocalDate.of(2025, 5, 9), "День Победы");
        HOLIDAYS_2025.put(LocalDate.of(2025, 6, 12), "День России");
        HOLIDAYS_2025.put(LocalDate.of(2025, 6, 13), "День России");
        HOLIDAYS_2025.put(LocalDate.of(2025, 11, 2), "День народного единства");
        HOLIDAYS_2025.put(LocalDate.of(2025, 11, 3), "День народного единства");
        HOLIDAYS_2025.put(LocalDate.of(2025, 11, 4), "День народного единства");
    }

    public boolean isHoliday(LocalDate date) {
        log.info("Проверяем является ли {} праздником", date);
        return HOLIDAYS_2025.containsKey(date);
    }

    public String getHolidayName(LocalDate date) {
        return HOLIDAYS_2025.get(date);
    }
}
