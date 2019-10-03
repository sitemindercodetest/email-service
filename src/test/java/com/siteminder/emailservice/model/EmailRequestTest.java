package com.siteminder.emailservice.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class EmailRequestTest {
    private final String json = String.format("{\"from\": \"t1@test.com\"," +
            " \"to\": [\"t2@test.com\", \"t3@test.com\"]," +
            " \"cc\": [\"t3@test.com\", \"t4@test.com\"]," +
            "\"bcc\": [\"t5@test.com\", \"t6@test.com\"]," +
            "\"subject\": \"subject of the email\"," +
            "\"body\": \"subject of the body\"}");
    private final ObjectMapper mapper = new ObjectMapper();


    @Test
    public void Should_Deserialize_From() throws IOException {
        EmailRequest emailRequest = mapper.readValue(json, EmailRequest.class);

        assertThat(email("t1@test.com"), equalTo(emailRequest.from));
    }

    @Test
    public void Should_Deserialize_To() throws IOException {
        EmailRequest emailRequest = mapper.readValue(json, EmailRequest.class);
        List<EmailRequest.Email> toEmail = Arrays.asList(email("t2@test.com"), email("t3@test.com"));

        assertThat(toEmail, equalTo(emailRequest.to));
    }

    @Test
    public void Should_Deserialize_CC() throws IOException {
        EmailRequest emailRequest = mapper.readValue(json, EmailRequest.class);
        List<EmailRequest.Email> ccEmails = Arrays.asList(email("t3@test.com"), email("t4@test.com"));

        assertThat(ccEmails, equalTo(emailRequest.cc));
    }

    @Test
    public void Should_Deserialize_BCC() throws IOException {
        EmailRequest emailRequest = mapper.readValue(json, EmailRequest.class);
        List<EmailRequest.Email> bccEmails = Arrays.asList(email("t5@test.com"), email("t6@test.com"));

        assertThat(bccEmails, equalTo(emailRequest.bcc));
    }

    @Test
    public void Should_Deserialize_Subject() throws IOException {
        EmailRequest emailRequest = mapper.readValue(json, EmailRequest.class);

        assertThat("subject of the email", equalTo(emailRequest.subject));
    }

    @Test
    public void Should_Deserialize_Body() throws IOException {
        EmailRequest emailRequest = mapper.readValue(json, EmailRequest.class);

        assertThat("subject of the body", equalTo(emailRequest.body));
    }

    private EmailRequest.Email email(String id) {
        return new EmailRequest.Email(id);
    }

}