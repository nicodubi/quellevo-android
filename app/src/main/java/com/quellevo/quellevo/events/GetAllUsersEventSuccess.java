package com.quellevo.quellevo.events;

import com.quellevo.quellevo.web_services.rest_entities.UserEvent;

import java.util.ArrayList;

/**
 * Created by Nicolas on 25/6/2017.
 */

public class GetAllUsersEventSuccess {
    private ArrayList<UserEvent> users;

    public GetAllUsersEventSuccess(ArrayList<UserEvent> users) {
        this.users = users;
    }

    public ArrayList<UserEvent> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<UserEvent> users) {
        this.users = users;
    }
}
