package com.quellevo.quellevo.events;


import com.quellevo.quellevo.web_services.rest_entities.CreateUserRequest;

/**
 * Created by Nicolas on 11/5/2017.
 */

public class CreateUserEvent {
    private CreateUserRequest request;

    public CreateUserEvent(CreateUserRequest request) {

        this.request = request;
    }

    public CreateUserRequest getRequest() {
        return request;
    }
}
