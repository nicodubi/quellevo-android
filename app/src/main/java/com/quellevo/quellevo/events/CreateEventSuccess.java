package com.quellevo.quellevo.events;

import com.quellevo.quellevo.web_services.rest_entities.Event;

/**
 * Created by Nicolas on 24/6/2017.
 */

public class CreateEventSuccess {
    private Event event;

    public CreateEventSuccess(Event response) {
        this.event = response;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
