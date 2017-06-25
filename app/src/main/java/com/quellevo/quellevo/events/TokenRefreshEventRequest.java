package com.quellevo.quellevo.events;


import com.quellevo.quellevo.web_services.rest_entities.CreateLoginEmailRequest;

/**
 * Created by Nicolas on 2/6/2017.
 */

public class TokenRefreshEventRequest {

    private CreateLoginEmailRequest request;

    public TokenRefreshEventRequest(CreateLoginEmailRequest request) {
        this.request = request;
    }

    public CreateLoginEmailRequest getRequest() {
        return request;
    }

    public void setRequest(CreateLoginEmailRequest request) {
        this.request = request;
    }
}
