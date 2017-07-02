package com.quellevo.quellevo.events;

import com.quellevo.quellevo.web_services.rest_entities.Event;

/**
 * Created by Nicolas on 1/7/2017.
 */

public class UpdateEventRequestSuccess {
    private Event event;

    public UpdateEventRequestSuccess(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
