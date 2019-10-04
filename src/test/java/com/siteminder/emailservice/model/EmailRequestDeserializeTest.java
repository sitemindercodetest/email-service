package com.siteminder.emailservice.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siteminder.emailservice.builders.EmailRequestBuilder;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class EmailRequestDeserializeTest {
    private final EmailRequest emailRequest = new EmailRequestBuilder().build();
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void Should_Deserialize_From() {
        assertThat("t1@test.com", equalTo(emailRequest.from));
    }

    @Test
    public void Should_Deserialize_To() {
        assertThat(Arrays.asList("t2@test.com", "t3@test.com"), equalTo(emailRequest.to));
    }

    @Test
    public void Should_Deserialize_CC() {
        assertThat(Arrays.asList("t3@test.com", "t4@test.com"), equalTo(emailRequest.cc));
    }

    @Test
    public void Should_Deserialize_BCC() {
        assertThat(Arrays.asList("t5@test.com", "t6@test.com"), equalTo(emailRequest.bcc));
    }

    @Test
    public void Should_Deserialize_Subject() {
        assertThat("subject of the email", equalTo(emailRequest.subject));
    }

    @Test
    public void Should_Deserialize_Body() {
        assertThat("body of the email", equalTo(emailRequest.body));
    }
}