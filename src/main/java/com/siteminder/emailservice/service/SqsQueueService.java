package com.siteminder.emailservice.service;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.siteminder.emailservice.model.EmailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import static org.springframework.cloud.aws.messaging.core.SqsMessageHeaders.SQS_DEDUPLICATION_ID_HEADER;
import static org.springframework.cloud.aws.messaging.core.SqsMessageHeaders.SQS_GROUP_ID_HEADER;

@Service
public class SqsQueueService implements QueueService {

    private final QueueMessagingTemplate queueMessagingTemplate;

    @Value("${sqs.queueName}")
    private String queueName;

    @Value("${sqs.groupId}")
    private String groupId;

    Logger logger = LoggerFactory.getLogger(SqsQueueService.class);

    @Autowired
    public SqsQueueService(AmazonSQSAsync amazonSqs) {
        this.queueMessagingTemplate = new QueueMessagingTemplate(amazonSqs);
    }

    public void sendMessage(EmailRequest req) {
        Message<EmailRequest> msg = MessageBuilder
                .withPayload(req)
                .setHeader(SQS_GROUP_ID_HEADER, groupId)
                .setHeader(SQS_DEDUPLICATION_ID_HEADER, String.valueOf(req.id))
                .build();
        queueMessagingTemplate.send(queueName, msg);
        logger.info("Added message to queue - " + req.id);
    }
}
