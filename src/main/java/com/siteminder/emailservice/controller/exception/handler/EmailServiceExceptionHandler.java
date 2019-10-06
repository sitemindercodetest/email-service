package com.siteminder.emailservice.controller.exception.handler;

import com.siteminder.emailservice.model.ApiErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class EmailServiceExceptionHandler extends ResponseEntityExceptionHandler {

    private final String shortStr = "short";
    private final String longStr = "long";
    private final String invalidData = "Invalid data";
    private final String messagingError = "Not able to add email in queue to process, Try again";
    private final Logger logger = LoggerFactory.getLogger(EmailServiceExceptionHandler.class);

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
        message.put(shortStr, invalidData);
        message.put(longStr, ex.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(status, message, errors);
        return handleExceptionInternal(ex, response, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        Map<String, String> message = new HashMap<>();
        message.put(shortStr, invalidData);
        message.put(longStr, ex.getMessage());
        ApiErrorResponse errorResponse = new ApiErrorResponse(status, message, Optional.empty());
        return handleExceptionInternal(ex, errorResponse, headers, status, request);

    }

    @ExceptionHandler({ MessagingException.class })
    public ResponseEntity<ApiErrorResponse> handleMessagingException(
            MessagingException ex,
            WebRequest request) {
        logger.info("Error while pushing message to queue", request);
        Map<String, String> message = new HashMap<>();
        message.put(shortStr, messagingError);
        message.put(longStr, ex.getMessage());
        ApiErrorResponse res = new ApiErrorResponse(INTERNAL_SERVER_ERROR, message,Optional.empty());
        return new ResponseEntity<ApiErrorResponse>(res, INTERNAL_SERVER_ERROR);
    }

}
