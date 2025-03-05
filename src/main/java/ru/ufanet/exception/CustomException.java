package com.effective.mobile.tskmngmntsystm.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomException extends RuntimeException {

    private String message;

    public CustomException(String msg, Exception exception) {
        super(msg, exception);
        this.message = msg;
    }
}
