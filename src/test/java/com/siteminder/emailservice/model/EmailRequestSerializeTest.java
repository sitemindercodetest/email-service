package com.siteminder.emailservice.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class EmailRequestSerializeTest {
    private final String json = String.format("{\"from\": \"t1@test.com\"," +
            " \"to\": [\"t2@test.com\", \"t3@test.com\"]," +
            " \"cc\": [\"t3@test.com\", \"t4@test.com\"]," +
            "\"bcc\": [\"t5@test.com\", \"t6@test.com\"]," +
            "\"subject\": \"subject of the email\"," +
            "\"body\": \"subject of the body\"}");
    private final ObjectMapper mapper = new ObjectMapper();
    private EmailRequest emailRequest;

    @Before
    public void setUp() throws IOException {
        emailRequest = mapper.readValue(json, EmailRequest.class);
    }

    @Test
    public void Should_Serialize_id() throws JsonProcessingException {
        String idJsonValue = "\"id\":" + emailRequest.id;
        assertThat(mapper.writeValueAsString(emailRequest), containsString(idJsonValue));
    }

    @Test
    public void Should_Serialize_from() throws JsonProcessingException {
        String fromJsonValue = "\"from\":\""+ emailRequest.from +"\"";
        assertThat(mapper.writeValueAsString(emailRequest), containsString(fromJsonValue));
    }

    @Test
    public void Should_Serialize_to() throws JsonProcessingException {
        String toJsonValue = "\"to\":[\"t2@test.com\",\"t3@test.com\"]";
        assertThat(mapper.writeValueAsString(emailRequest), containsString(toJsonValue));
    }

    @Test
    public void Should_Serialize_cc() throws JsonProcessingException {
        String ccJsonValue = "\"cc\":[\"t3@test.com\",\"t4@test.com\"]";
        assertThat(mapper.writeValueAsString(emailRequest), containsString(ccJsonValue));
    }

    @Test
    public void Should_Serialize_bcc() throws JsonProcessingException {
        String bccJsonValue = "\"bcc\":[\"t5@test.com\",\"t6@test.com\"]";
        assertThat(mapper.writeValueAsString(emailRequest), containsString(bccJsonValue));
    }

    @Test
    public void Should_Serialize_subject() throws JsonProcessingException {
        String subjectJsonValue = "\"subject\":\""+ emailRequest.subject +"\"";
        assertThat(mapper.writeValueAsString(emailRequest), containsString(subjectJsonValue));
    }

    @Test
    public void Should_Serialize_body() throws JsonProcessingException {
        String bodyJsonValue = "\"body\":\""+ emailRequest.body +"\"";;
        assertThat(mapper.writeValueAsString(emailRequest), containsString(bodyJsonValue));
    }
}