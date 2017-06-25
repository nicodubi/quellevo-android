package com.quellevo.quellevo.events;


import com.quellevo.quellevo.web_services.rest_entities.UserSignUpInfo;

/**
 * Created by Nicolas on 11/5/2017.
 */

public class CreateUserEventSuccess {

    UserSignUpInfo response;

    public CreateUserEventSuccess(UserSignUpInfo response) {
        this.response = response;
    }

    public UserSignUpInfo getResponse() {
        return response;
    }
}
