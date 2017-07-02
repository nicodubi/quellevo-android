package com.quellevo.quellevo.web_services.rest_entities;

/**
 * Created by Nicolas on 25/6/2017.
 */

public class FirebaseBody {

    private String token;

    public FirebaseBody(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
