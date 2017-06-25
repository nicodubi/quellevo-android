package com.quellevo.quellevo.web_services.rest_entities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Nicolas on 23/6/2017.
 */

public class Event {
    private Long id;
    private String name;
    private Date date;
    private ArrayList<UserEventResponse> event_users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<UserEventResponse> getEvent_users() {
        return event_users;
    }

    public void setEvent_users(ArrayList<UserEventResponse> event_users) {
        this.event_users = event_users;
    }

    public String getDateString() {
        String dateString = (new SimpleDateFormat("HH:mm dd/MM/yyyy").format(date));
        return dateString;
    }
}
