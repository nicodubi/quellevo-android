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
    private ArrayList<UserEvent> event_users;
    private ArrayList<EventItem> items_without_assignment;

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

    public ArrayList<UserEvent> getEvent_users() {
        return event_users;
    }

    public void setEvent_users(ArrayList<UserEvent> event_users) {
        this.event_users = event_users;
    }

    public String getDateString() {
        String dateString = (new SimpleDateFormat("HH:mm dd/MM/yyyy").format(date));
        return dateString;
    }

    public ArrayList<EventItem> getItems_without_assignment() {
        return items_without_assignment;
    }

    public void setItems_without_assignment(ArrayList<EventItem> items_without_assignment) {
        this.items_without_assignment = items_without_assignment;
    }
}
