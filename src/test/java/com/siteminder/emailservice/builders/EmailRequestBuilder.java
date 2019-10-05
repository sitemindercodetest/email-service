package com.siteminder.emailservice.builders;

import com.siteminder.emailservice.model.EmailRequest;

import java.util.Arrays;
import java.util.List;

public class EmailRequestBuilder {

    private String from = "t1@test.com";
    private List<String> to = Arrays.asList("t2@test.com", "t3@test.com");
    private List<String> cc = Arrays.asList("t3@test.com", "t4@test.com");
    private List<String> bcc = Arrays.asList("t5@test.com", "t6@test.com");
    private String subject = "subject of the email";
    private String body = "body of the email";
    private long sendAt = 1570246978;

    public EmailRequestBuilder withFrom(String from) {
        this.from = from;
        return this;
    }

    public EmailRequestBuilder withTo(List<String> to) {
        this.to = to;
        return this;
    }

    public EmailRequestBuilder withCC(List<String> cc) {
        this.cc = cc;
        return this;
    }

    public EmailRequestBuilder withBcc(List<String> bcc) {
        this.bcc = bcc;
        return this;
    }

    public EmailRequestBuilder withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public EmailRequestBuilder withBody(String body) {
        this.body = body;
        return this;
    }

    public EmailRequestBuilder withSendAt(long epochTime) {
        this.sendAt = epochTime;
        return this;
    }

    public EmailRequest build(){
        return new EmailRequest(from, to, cc, bcc, subject, body, sendAt);
    }

}
