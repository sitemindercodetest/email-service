package com.siteminder.emailservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Objects;

public class EmailRequest {

    @NotEmpty
    public final Email from;

    @NotEmpty
    public final List<Email> to;

    public final List<Email> cc;
    public final List<Email> bcc;
    public final String subject;
    public final String body;


    @JsonCreator
    public EmailRequest(@JsonProperty("from") Email from,
                        @JsonProperty("to") List<Email> to,
                        @JsonProperty("cc") List<Email> cc,
                        @JsonProperty("bcc") List<Email> bcc,
                        @JsonProperty("subject") String subject,
                        @JsonProperty("body") String body) {
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.subject = subject;
        this.body = body;
    }

    static class Email {

        @NotEmpty
        @javax.validation.constraints.Email
        @JsonValue
        public final String id;

        public Email(String id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Email email = (Email) o;
            return Objects.equals(id, email.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
}
