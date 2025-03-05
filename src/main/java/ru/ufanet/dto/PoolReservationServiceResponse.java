package com.effective.mobile.tskmngmntsystm.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class TaskServiceResponse<T> {

    private int status;
    private String message;
    private T body;

    public static <T> TaskServiceResponse<T> ok(String message, T body) {
        TaskServiceResponse<T> taskServiceResponse = new TaskServiceResponse<>();
        taskServiceResponse.message = message;
        taskServiceResponse.body = body;
        taskServiceResponse.status = HttpStatus.OK.value();
        return taskServiceResponse;
    }

    public static <T> TaskServiceResponse<T> ok(String message) {

        TaskServiceResponse<T> taskServiceResponse = new TaskServiceResponse<>();
        taskServiceResponse.message = message;
        taskServiceResponse.status = HttpStatus.OK.value();
        return taskServiceResponse;
    }

    public static <T> TaskServiceResponse<T> notOk(String message, HttpStatus httpStatus) {
        TaskServiceResponse<T> taskServiceResponse = new TaskServiceResponse<>();
        taskServiceResponse.message = message;
        taskServiceResponse.status = httpStatus.value();
        return taskServiceResponse;
    }
}
