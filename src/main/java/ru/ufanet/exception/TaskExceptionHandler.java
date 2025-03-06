package ru.ufanet.exception;


import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.ufanet.dto.PoolReservationServiceResponse;

import javax.naming.AuthenticationException;

@Slf4j
@RestControllerAdvice
public class TaskExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public <T> PoolReservationServiceResponse<T> handleEntityNotFoundException(EntityNotFoundException ex) {
        log.error("Не найдено. Error: {}", ex.getMessage());
        return PoolReservationServiceResponse.notOk(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public <T> PoolReservationServiceResponse<T> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Некорректный формат входнных данных. Error:{} ", ex.getFieldError().getDefaultMessage());
        return PoolReservationServiceResponse.notOk("Некорректный формат входных данных. Поле " + ex.getFieldError().getField() + ". " + ex.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public <T> PoolReservationServiceResponse<T> handleCustomException(CustomException ex) {
        log.error(ex.getMessage());
        return PoolReservationServiceResponse.notOk(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public <T> PoolReservationServiceResponse<T> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error(ex.getMessage());
        return PoolReservationServiceResponse.notOk(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public <T> PoolReservationServiceResponse<T> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("Некорректный формат входных данных. Error: {}", ex.getMessage());
        return PoolReservationServiceResponse.notOk("Некорректный формат входных данных. Поле " + ex.getMessage(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(AccessDeniedException.class)
    public <T> PoolReservationServiceResponse<T> handleAccessDeniedException(AccessDeniedException ex) {
        log.error("Нет доступа. Error: {}", ex.getMessage());
        return PoolReservationServiceResponse.notOk("Нет доступа. Error: " + ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler({ AuthenticationException.class })
    public <T> PoolReservationServiceResponse<T> handleAuthenticationException(AuthenticationException ex) {
        log.error("Нет доступа. Error: {}", ex.getMessage());
        return PoolReservationServiceResponse.notOk("Нет доступа. Error: " + ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
