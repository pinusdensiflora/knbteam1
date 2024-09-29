package com.knbteam1.inuri.auth.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class UserAlreadyExistsException extends HttpClientErrorException {

    public UserAlreadyExistsException() {super(HttpStatus.CONFLICT, "Use already exists");}

    public UserAlreadyExistsException(String username){
        super(HttpStatus.CONFLICT, username + "already exists");
    }
}
