package com.siteminder.emailservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.siteminder.emailservice.validators.Emails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Objects;

public class EmailRequest {

    @NotEmpty
    @Email
    public final String from;

    public final String id;

    @NotEmpty
    @Emails
    public final List<String> to;

    @Emails
    public final List<String> cc;

    @Emails
    public final List<String> bcc;
    public final String subject;
    public final String body;


    @JsonCreator
    public EmailRequest(@JsonProperty("from") String from,
                        @JsonProperty("to") List<String> to,
                        @JsonProperty("cc") List<String> cc,
                        @JsonProperty("bcc") List<String> bcc,
                        @JsonProperty("subject") String subject,
                        @JsonProperty("body") String body) {
        this.id = String.valueOf(this.hashCode());
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.subject = subject;
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmailRequest)) return false;
        EmailRequest that = (EmailRequest) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, cc, bcc, subject, body);
    }
}
