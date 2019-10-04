package com.siteminder.emailservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiErrorResponse {
    public final HttpStatus status;
    public final Map<String, String> message;
    public final Optional<Map<String, String>> errors;

    @JsonCreator
    public ApiErrorResponse(@JsonProperty("status") HttpStatus status,
                            @JsonProperty("message") Map<String, String> message,
                            @JsonProperty("errors") Optional<Map<String, String>> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

}
