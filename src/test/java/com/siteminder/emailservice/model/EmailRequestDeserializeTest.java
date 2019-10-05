package com.siteminder.emailservice.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siteminder.emailservice.builders.EmailRequestBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class EmailRequestDeserializeTest {
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
    public void Should_Deserialize_From() {
        assertThat(emailRequest.from, equalTo("t1@test.com"));
    }

    @Test
    public void Should_Deserialize_To() {
        assertThat(emailRequest.to, equalTo(Arrays.asList("t2@test.com", "t3@test.com")));
    }

    @Test
    public void Should_Deserialize_CC() {
        assertThat(emailRequest.cc, equalTo(Arrays.asList("t3@test.com", "t4@test.com")));
    }

    @Test
    public void Should_Deserialize_BCC() {
        assertThat(emailRequest.bcc, equalTo(Arrays.asList("t5@test.com", "t6@test.com")));
    }

    @Test
    public void Should_Deserialize_Subject() {
        assertThat(emailRequest.subject, equalTo("subject of the email"));
    }

    @Test
    public void Should_Deserialize_Body() {
        assertThat(emailRequest.body, equalTo("subject of the body"));
    }

    @Test
    public void Should_ID_MatchesIfSameAttributes() {
        EmailRequest emailRequest = new EmailRequestBuilder().build();
        EmailRequest emailRequestCopy = new EmailRequestBuilder().build();

        assertEquals(emailRequest.id, emailRequestCopy.id);
    }

    @Test
    public void Should_ID_DifferentIfAttributesDontMatch() {
        EmailRequest emailRequest = new EmailRequestBuilder().build();
        EmailRequest differEmailRequest = new EmailRequestBuilder().withFrom("asd@as.com").build();

        assertNotEquals(emailRequest.id, differEmailRequest.id);
    }
}
