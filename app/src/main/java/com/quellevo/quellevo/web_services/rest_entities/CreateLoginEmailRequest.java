package com.quellevo.quellevo.web_services.rest_entities;


import com.quellevo.quellevo.utils.Constants;

/**
 * Created by Nicolas on 18/5/2017.
 */

public class CreateLoginEmailRequest {

    private String email;
    private String password;
    private String grant_type;

    public CreateLoginEmailRequest(String email, String password) {
        this.email = email;
        this.password = password;
        this.grant_type = "password";
    }

    public CreateLoginEmailRequest(){
        this.grant_type = "password";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGrant_type() {
        return grant_type;
    }

}
