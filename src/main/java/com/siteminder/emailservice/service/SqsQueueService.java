package com.siteminder.emailservice.service;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.siteminder.emailservice.model.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SqsQueueService implements QueueService {

    private final QueueMessagingTemplate queueMessagingTemplate;

    @Autowired
    public SqsQueueService(AmazonSQSAsync amazonSqs) {
        this.queueMessagingTemplate = new QueueMessagingTemplate(amazonSqs);
    }

    public void sendMessage(EmailRequest req) {
        queueMessagingTemplate.convertAndSend("st-email", req);
    }
}
