package ru.ufanet.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class PoolReservationServiceResponse<T> {

    private int status;
    private String message;
    private T body;

    public static <T> PoolReservationServiceResponse<T> ok(String message, T body) {
        PoolReservationServiceResponse<T> poolReservationServiceResponse = new PoolReservationServiceResponse<>();
        poolReservationServiceResponse.message = message;
        poolReservationServiceResponse.body = body;
        poolReservationServiceResponse.status = HttpStatus.OK.value();
        return poolReservationServiceResponse;
    }

    public static <T> PoolReservationServiceResponse<T> ok(String message) {

        PoolReservationServiceResponse<T> poolReservationServiceResponse = new PoolReservationServiceResponse<>();
        poolReservationServiceResponse.message = message;
        poolReservationServiceResponse.status = HttpStatus.OK.value();
        return poolReservationServiceResponse;
    }

    public static <T> PoolReservationServiceResponse<T> notOk(String message, HttpStatus httpStatus) {
        PoolReservationServiceResponse<T> poolReservationServiceResponse = new PoolReservationServiceResponse<>();
        poolReservationServiceResponse.message = message;
        poolReservationServiceResponse.status = httpStatus.value();
        return poolReservationServiceResponse;
    }
}
