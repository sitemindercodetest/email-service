package com.siteminder.emailservice.controller.exception.handler;

import com.siteminder.emailservice.model.ApiErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@ControllerAdvice
public class EmailServiceExceptionHandler extends ResponseEntityExceptionHandler {

    private final String SHORT = "short";
    private final String LONG = "long";
    private final String INVALID_DATA = "Invalid data";
    private final String MESSAGING_ERROR = "Not able to add email in queue to process, Try again";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        Optional<Map<String, String>> errors = Optional.of(ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
        Map<String, String> message = new HashMap<>();
        message.put(SHORT, INVALID_DATA);
        message.put(LONG, ex.getMessage());
        ApiErrorResponse response
                = new ApiErrorResponse(
                status,
                message,
                errors);
        return handleExceptionInternal(ex, response, headers, status, request);
    }

    @ExceptionHandler({ MessagingException.class })
    public ResponseEntity<ApiErrorResponse> handleMessagingException(
            MessagingException ex,
            WebRequest request) {
        Map<String, String> message = new HashMap<>();
        message.put(SHORT, MESSAGING_ERROR);
        message.put(LONG, ex.getMessage());
        ApiErrorResponse res = new ApiErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                message,
                Optional.empty()
        );
        return new ResponseEntity<ApiErrorResponse>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
