package com.quellevo.quellevo.events;

import com.quellevo.quellevo.web_services.rest_entities.UserEvent;

import java.util.ArrayList;

/**
 * Created by Nicolas on 25/6/2017.
 */

public class PostNewUsersToEventRequest {
    private ArrayList<UserEvent> users;
    private Long eventId;

    public PostNewUsersToEventRequest(ArrayList<UserEvent> users, Long eventId) {
        this.users = users;
        this.eventId = eventId;
    }

    public Long getEventId() {
        return eventId;
    }

    public String getUser_ids() {
        String coma = ",";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        for (UserEvent user : users) {
            stringBuilder.append(user.getId()).append(coma);
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }
}
