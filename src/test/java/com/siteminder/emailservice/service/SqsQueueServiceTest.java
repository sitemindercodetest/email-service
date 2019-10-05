package com.siteminder.emailservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siteminder.emailservice.builders.EmailRequestBuilder;
import com.siteminder.emailservice.model.EmailRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.Message;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SqsQueueServiceTest {

    final ArgumentCaptor<Message> captor = ArgumentCaptor.forClass(Message.class);

    @Mock
    private QueueMessagingTemplate queueMessagingTemplate;

    @InjectMocks
    private SqsQueueService service;

    @Test
    public void should() throws IOException {
        EmailRequest givenEmailReq = new EmailRequestBuilder().build();
        service.sendMessage(givenEmailReq);
        verify(queueMessagingTemplate).send((String) isNull(), captor.capture());
        Message message = captor.getValue();

        Object payload = message.getPayload();
        EmailRequest actualEmailReq = new ObjectMapper().readValue(payload.toString(), EmailRequest.class);
        assertEquals(givenEmailReq, actualEmailReq);
    }
}
