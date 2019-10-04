package com.siteminder.emailservice.service;

import com.siteminder.emailservice.model.EmailRequest;

public interface QueueService {
    void sendMessage(EmailRequest req);
}
