package com.tcscontrol.control_backend;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tcscontrol.control_backend.exception.RecordNotFoundException;

@RestControllerAdvice
public class ApplicationControllerAdvice {
    
    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(RecordNotFoundException ex){
        return ex.getMessage();
    }
}
