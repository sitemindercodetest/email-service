package com.siteminder.emailservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.siteminder.emailservice.model.EmailRequest;

public interface QueueService {
    void sendMessage(EmailRequest req) throws JsonProcessingException;
}
