package org.c4sg.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class EmailDTO {

    @NotEmpty(message = "Please enter from email address")
    @Email(message = "Email address is not valid")
    private String from;

    @NotEmpty(message = "Please enter to email address")
    @Email(message = "Email address is not valid")
    private String to;

    @Email(message = "Email address is not valid")
    private String replyTo;

    @NotEmpty(message = "Please provide email subject")
    private String subject;

    @NotEmpty(message = "Please provide email body")
    private String body;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
