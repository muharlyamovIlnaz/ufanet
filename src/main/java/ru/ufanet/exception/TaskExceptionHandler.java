package com.effective.mobile.tskmngmntsystm.exception;


import com.effective.mobile.tskmngmntsystm.dto.TaskServiceResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class TaskExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public <T> TaskServiceResponse<T> handleEntityNotFoundException(EntityNotFoundException ex) {
        log.error("Не найдено. Error: {}", ex.getMessage());
        return TaskServiceResponse.notOk(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public <T> TaskServiceResponse<T> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Некорректный формат входнных данных. Error:{} ", ex.getFieldError().getDefaultMessage());
        return TaskServiceResponse.notOk("Некорректный формат входных данных. Поле " + ex.getFieldError().getField() + ". " + ex.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public <T> TaskServiceResponse<T> handleCustomException(CustomException ex) {
        log.error(ex.getMessage());
        return TaskServiceResponse.notOk(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public <T> TaskServiceResponse<T> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error(ex.getMessage());
        return TaskServiceResponse.notOk(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public <T> TaskServiceResponse<T> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("Некорректный формат входных данных. Error: {}", ex.getMessage());
        return TaskServiceResponse.notOk("Некорректный формат входных данных. Поле " + ex.getMessage(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(AccessDeniedException.class)
    public <T> TaskServiceResponse<T> handleAccessDeniedException(AccessDeniedException ex) {
        log.error("Нет доступа. Error: {}", ex.getMessage());
        return TaskServiceResponse.notOk("Нет доступа. Error: " + ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }


}
