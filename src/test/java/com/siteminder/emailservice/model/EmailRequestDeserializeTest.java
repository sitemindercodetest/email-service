package com.siteminder.emailservice.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class EmailRequestDeserializeTest {
    private final EmailRequest emailRequest = new EmailRequest(
            "t1@test.com",
            Arrays.asList("t2@test.com", "t3@test.com"),
            Arrays.asList("t3@test.com", "t4@test.com"),
            Arrays.asList("t5@test.com", "t6@test.com"),
            "subject of the email",
            "body of the email"
    );
    private final ObjectMapper mapper = new ObjectMapper();
    private String jsonValue;

    @Before
    public void setUp() throws Exception {
        jsonValue = mapper.writeValueAsString(emailRequest);
    }

    @Test
    public void Should_Deserialize_From() throws IOException {
        assertThat("t1@test.com", equalTo(emailRequest.from));
    }

    @Test
    public void Should_Deserialize_To() throws IOException {
        assertThat(Arrays.asList("t2@test.com", "t3@test.com"), equalTo(emailRequest.to));
    }

    @Test
    public void Should_Deserialize_CC() throws IOException {
        assertThat(Arrays.asList("t3@test.com", "t4@test.com"), equalTo(emailRequest.cc));
    }

    @Test
    public void Should_Deserialize_BCC() throws IOException {
        assertThat(Arrays.asList("t5@test.com", "t6@test.com"), equalTo(emailRequest.bcc));
    }

    @Test
    public void Should_Deserialize_Subject() throws IOException {
        assertThat("subject of the email", equalTo(emailRequest.subject));
    }

    @Test
    public void Should_Deserialize_Body() throws IOException {
        assertThat("body of the email", equalTo(emailRequest.body));
    }
}