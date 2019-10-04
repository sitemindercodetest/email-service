package com.siteminder.emailservice.service;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.siteminder.emailservice.model.EmailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SqsQueueService implements QueueService {

    private final QueueMessagingTemplate queueMessagingTemplate;

    @Value("${sqs.queueName}")
    private String queueName;

    Logger logger = LoggerFactory.getLogger(SqsQueueService.class);

    @Autowired
    public SqsQueueService(AmazonSQSAsync amazonSqs) {
        this.queueMessagingTemplate = new QueueMessagingTemplate(amazonSqs);
    }

    public void sendMessage(EmailRequest req) {
        queueMessagingTemplate.convertAndSend(queueName, req);
        logger.info("Added message to queue - " + req.id);
    }
}
