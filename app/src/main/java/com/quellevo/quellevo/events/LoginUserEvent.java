package com.quellevo.quellevo.events;


import com.quellevo.quellevo.web_services.rest_entities.CreateLoginEmailRequest;

/**
 * Created by Nicolas on 16/5/2017.
 */

public class LoginUserEvent {

    private CreateLoginEmailRequest request;

    public LoginUserEvent(CreateLoginEmailRequest request) {

        this.request = request;
    }

    public CreateLoginEmailRequest getRequest() {
        return request;
    }
}
