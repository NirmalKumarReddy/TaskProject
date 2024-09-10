package com.demo.taskproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class APIExecption extends RuntimeException{
private String message;
public APIExecption(String message){
    super(message);
    this.message=message;
}
}
