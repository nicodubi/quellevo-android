package com.quellevo.quellevo.web_services.rest_entities;

/**
 * Created by Nicolas on 25/6/2017.
 */

public class AddUserBody {

    private String user_ids;

    public AddUserBody(String user_ids) {
        this.user_ids = user_ids;
    }

    public String getUser_ids() {
        return user_ids;
    }

    public void setUser_ids(String user_ids) {
        this.user_ids = user_ids;
    }
}
