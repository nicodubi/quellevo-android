package com.quellevo.quellevo.web_services.rest_entities;

import java.util.ArrayList;

/**
 * Created by Nicolas on 24/6/2017.
 */

public class UserEvent {

    private Long id;
    private String name;
    private String surname;
    private ArrayList<EventItem> event_items;

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public ArrayList<EventItem> getEvent_items() {
        return event_items;
    }

    public void setEvent_items(ArrayList<EventItem> event_items) {
        this.event_items = event_items;
    }
}
