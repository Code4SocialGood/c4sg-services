package org.c4sg.controller;

import org.c4sg.dto.MessageDTO;
import org.c4sg.exception.BadRequestException;
import org.c4sg.exception.NotFoundException;
import org.c4sg.exception.UserProjectException;
import org.c4sg.exception.slack.SlackArgumentException;
import org.c4sg.exception.slack.SlackResponseErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@CrossOrigin
@RestControllerAdvice
public class C4SGController {

    @CrossOrigin
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public MessageDTO exception(NotFoundException e) {
        return new MessageDTO(e.getMessage());
    }    
    
    @CrossOrigin
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public MessageDTO exception(BadRequestException e) {
        return new MessageDTO("Error from Global BadRequest. "+e.getMessage());
    }
    
    @CrossOrigin
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserProjectException.class)
    public MessageDTO exception(UserProjectException e) {
        return new MessageDTO(e.getMessage());
    }
        
    @CrossOrigin
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SlackArgumentException.class)
    public MessageDTO exception(SlackArgumentException e) {
        return new MessageDTO(e.getMessage());
    }
    
    @CrossOrigin
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SlackResponseErrorException.class)
    public MessageDTO exception(SlackResponseErrorException e) {
        return new MessageDTO(e.getMessage());
    }
}
