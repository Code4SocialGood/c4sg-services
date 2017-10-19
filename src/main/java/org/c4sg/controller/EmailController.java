package org.c4sg.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.c4sg.dto.EmailDTO;
import org.c4sg.exception.C4SGException;
import org.c4sg.service.AsyncEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/email")
@Api(description = "Operations about email", tags = "email")
public class EmailController {
    private static Logger LOG = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private AsyncEmailService asyncEmailService;

    @CrossOrigin
    @PostMapping(value = "/send")
    @ApiOperation(value = "Send email", notes = "Sends an email")
    public void sendEmail(@ApiParam(value = "Email in body", required = true)
                          @Valid @RequestBody EmailDTO email) {
        try {
            asyncEmailService.send(email);
        } catch (IOException e) {
            LOG.error("Email sending failed", e);
            throw new C4SGException("Email sending failed");
        }
    }

}
