package com.quellevo.quellevo.events;

import com.quellevo.quellevo.web_services.rest_entities.Event;

import java.util.ArrayList;

/**
 * Created by Nicolas on 25/6/2017.
 */

public class GetAllEventsSuccess {
    private ArrayList<Event> events;

    public GetAllEventsSuccess(ArrayList<Event> events) {
        this.events = events;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}
