package com.siteminder.emailservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Value("${sqs.queueName}")
    private String queueName;

    @Value("${sqs.groupId}")
    private String groupId;

    Logger logger = LoggerFactory.getLogger(SqsQueueService.class);

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    public void sendMessage(EmailRequest req) throws JsonProcessingException {
        Message<String> msg = MessageBuilder
                .withPayload(convertToJson(req))
                .setHeader(SQS_GROUP_ID_HEADER, groupId)
                .setHeader(SQS_DEDUPLICATION_ID_HEADER, String.valueOf(req.id))
                .build();
        queueMessagingTemplate.send(queueName, msg);
        logger.info("Added message to queue - " + req.id);
    }

    private String convertToJson(EmailRequest req) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(req);
    }
}
