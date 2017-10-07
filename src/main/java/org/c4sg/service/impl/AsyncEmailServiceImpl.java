package org.c4sg.service.impl;


import org.c4sg.dto.EmailDTO;
import org.c4sg.service.AsyncEmailService;
import org.c4sg.service.C4sgUrlService;
import org.c4sg.service.EmailTemplateService;
import org.c4sg.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.sendgrid.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AsyncEmailServiceImpl implements AsyncEmailService {

    @Autowired
    private EmailTemplateService service;

    @Value("${sendgrid_api_key}")
    private String sendgridApiKey;


    /**
     * Sends an email message asynchronously through SendGrid.
     * Status Code: 202	- ACCEPTED: Your message is both valid, and queued to be delivered.
     *
     * @param from      email address from which the message will be sent.
     * @param recipient array of strings containing the recipients of the message.
     * @param subject   subject header field.
     * @param text      content of the message.
     */
    @Async
    public void send(String from, String recipient, String replyTo, String subject, String text) throws IOException {

        Email emailFrom = new Email(from);
        String emailSubject = subject;
        Email emailTo = new Email(recipient);

        Content emailContent = new Content("text/html", text);
        Mail mail = new Mail(emailFrom, emailSubject, emailTo, emailContent);
        if (!replyTo.isEmpty()) {
            Email emailReplyTo = new Email(replyTo);
            mail.setReplyTo(emailReplyTo);
        }

        SendGrid sg = new SendGrid(sendgridApiKey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        sg.api(request);
    }

    @Override
    public void send(EmailDTO email) throws IOException {
        send(email.getFrom(), email.getTo(), email.getReplyTo(), email.getSubject(), email.getBody());
    }

    @Override
    public void sendWithContext(String from, String recipient, String replyTo, String subject, String template, Map<String, Object> mailContext) {

        String text = service.generateFromContext(mailContext, template);
        try {
            send(from, recipient, replyTo, subject, text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

