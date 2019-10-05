package com.siteminder.emailservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.siteminder.emailservice.model.ApiErrorResponse;
import com.siteminder.emailservice.model.EmailRequest;
import com.siteminder.emailservice.service.QueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(EmailServiceController.class);

    @PostMapping(path = "/email")
    @ResponseBody
    public ResponseEntity<ApiErrorResponse> addEmailToQueue(@Valid @RequestBody EmailRequest req)
            throws JsonProcessingException {
        logger.info("Received email request - " + req.id);
        service.sendMessage(req);
        ResponseEntity<ApiErrorResponse> responseEntity =
                new ResponseEntity<ApiErrorResponse>(HttpStatus.ACCEPTED);
        return responseEntity;
    }
}
