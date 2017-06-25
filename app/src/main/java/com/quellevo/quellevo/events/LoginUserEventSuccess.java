package com.quellevo.quellevo.events;


import com.quellevo.quellevo.web_services.rest_entities.UserLoginResponse;

/**
 * Created by Nicolas on 16/5/2017.
 */

public class LoginUserEventSuccess {
    private UserLoginResponse loginResponse;

    public LoginUserEventSuccess(UserLoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }

    public UserLoginResponse getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(UserLoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }
}
