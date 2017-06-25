package com.quellevo.quellevo.events;


import com.quellevo.quellevo.web_services.rest_entities.UserLoginResponse;

/**
 * Created by Nicolas on 2/6/2017.
 */

public class TokenRefreshEventSuccess {

    private UserLoginResponse loginResponse;

    public TokenRefreshEventSuccess(UserLoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }

    public UserLoginResponse getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(UserLoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }
}
