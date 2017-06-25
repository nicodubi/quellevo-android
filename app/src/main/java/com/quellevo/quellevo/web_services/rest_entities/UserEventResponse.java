package com.quellevo.quellevo.web_services.rest_entities;

import java.util.ArrayList;

/**
 * Created by Nicolas on 23/6/2017.
 */

public class UserEventResponse {
    private UserEvent user;
    private ArrayList<EventItem> event_items;

    public UserEvent getUser() {
        return user;
    }

    public void setUser(UserEvent user) {
        this.user = user;
    }

    public ArrayList<EventItem> getEvent_items() {
        return event_items;
    }

    public void setEvent_items(ArrayList<EventItem> event_items) {
        this.event_items = event_items;
    }
}
