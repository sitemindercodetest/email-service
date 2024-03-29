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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestOnlyApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmailServiceControllerIntegrationTest {

    private final String apiEndpoint = "/api/v1/email";
    @Autowired
    private MockMvc mvc;

    @MockBean
    private QueueService service;

    private EmailRequestBuilder builder = new EmailRequestBuilder();


    @Test
    public void Should_AddEmailToQueue_Success() throws Exception {
        String json = new ObjectMapper().writeValueAsString(builder.build());

        mvc.perform(post(apiEndpoint)
                .content(json)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    public void Should_CustomErrorMsg_ForMissingData() throws Exception {
        String json = new ObjectMapper().writeValueAsString(builder.withFrom("").build());

        mvc.perform(post(apiEndpoint)
                .content(json)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.from").value("must not be empty"));
    }

    @Test
    public void Should_CustomErrorMsg_MessagingException() throws Exception {
        String json = new ObjectMapper().writeValueAsString(builder.build());
        doThrow(new MessagingException("custom error")).when(service).sendMessage(any());
        mvc.perform(post(apiEndpoint)
                .content(json)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message.short").value("Not able to add email in queue to process, Try again"));
    }

    @Test
    public void Should_CustomErrorMsg_InvalidData() throws Exception {
        String json = "{invalidJson}";

        mvc.perform(post(apiEndpoint)
                .content(json)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message.short").value("Invalid data"));
    }

    @Test
    public void Should_CustomErrorMsg_InvalidMethod() throws Exception {
        String json = new ObjectMapper().writeValueAsString(builder.build());

        mvc.perform(get(apiEndpoint)
                .content(json)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.message.short").value("Not supported"));
    }

    @Test
    public void Should_CustomErrorMsg_InvalidMediaType() throws Exception {
        String json = new ObjectMapper().writeValueAsString(builder.build());

        mvc.perform(post(apiEndpoint)
                .content(json)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(jsonPath("$.message.short").value("Not supported"));
    }
}
