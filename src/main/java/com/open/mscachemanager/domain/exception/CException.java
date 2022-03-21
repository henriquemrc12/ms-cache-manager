package com.open.mscachemanager.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;


@ResponseStatus()
public class CException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private HttpStatus status = HttpStatus.BAD_REQUEST;

    public CException(String message) {
        super(message);
    }

    public CException(List<String> messages) {
        super(messages.toString());
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}