package com.quellevo.quellevo.events;

import com.quellevo.quellevo.web_services.rest_entities.Event;

/**
 * Created by Nicolas on 25/6/2017.
 */

public class PostNewUsertToEventSuccess {
    private Event event;

    public PostNewUsertToEventSuccess(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
