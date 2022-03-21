package com.open.mscachemanager.domain.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@SuppressWarnings({"unchecked","rawtypes"})
@ControllerAdvice
public class CustomErrorException extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
            final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        final ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(status.value());

        final List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        exceptionResponse.setMessages(errors);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(CException.class)
    public final ResponseEntity<ExceptionResponse> handleNotFoundException(final CException ex, final WebRequest request) {

        String error = ex.getMessage().replace("[", "").replace("]","");
        List<String> errorList = new ArrayList<>(Arrays.asList(error.split(",")));

        ExceptionResponse exceptionResponse = new ExceptionResponse(errorList,ex.getStatus().value());
        return new ResponseEntity<>(exceptionResponse, ex.getStatus());
    }


}