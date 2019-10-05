package com.siteminder.emailservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siteminder.emailservice.TestOnlyApplication;
import com.siteminder.emailservice.builders.EmailRequestBuilder;
import com.siteminder.emailservice.service.QueueService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.messaging.MessagingException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestOnlyApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmailServiceControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private QueueService service;

    private EmailRequestBuilder builder = new EmailRequestBuilder();


    @Test
    public void Should_AddEmailToQueue_Success() throws Exception {
        String json = new ObjectMapper().writeValueAsString(builder.build());

        mvc.perform(post("/email")
                .content(json)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    public void Should_AddEmailToQueue_Bad_Request() throws Exception {
        String json = new ObjectMapper().writeValueAsString(builder.withFrom("").build());

        mvc.perform(post("/email")
                .content(json)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.from").value("must not be empty"));
    }

    @Test
    public void Should_AddEmailToQueue_Messaging_Exception() throws Exception {
        String json = new ObjectMapper().writeValueAsString(builder.build());
        doThrow(new MessagingException("custom error")).when(service).sendMessage(any());
        mvc.perform(post("/email")
                .content(json)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message.short").value("Not able to add email in queue to process, Try again"));
    }
}
