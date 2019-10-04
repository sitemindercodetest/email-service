package com.siteminder.emailservice.controller;

import com.siteminder.emailservice.model.EmailRequest;
import com.siteminder.emailservice.model.ApiErrorResponse;
import com.siteminder.emailservice.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class EmailServiceController {

    @Autowired
    private QueueService service;

    @PostMapping(path = "/email")
    @ResponseBody
    public ResponseEntity<ApiErrorResponse> addEmailToQueue(@Valid @RequestBody EmailRequest req) {
        service.sendMessage(req);
        ResponseEntity<ApiErrorResponse> responseEntity =
                new ResponseEntity<ApiErrorResponse>(HttpStatus.ACCEPTED);
        return responseEntity;
    }
}
