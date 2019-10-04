package com.siteminder.emailservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    public final List<@Email String> to;

    public final List<@Email String> cc;
    public final List<@Email String> bcc;
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
    public int hashCode() {
        return Objects.hash(from, to, cc, bcc, subject, body);
    }
}
