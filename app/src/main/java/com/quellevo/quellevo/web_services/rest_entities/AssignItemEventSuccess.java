package com.quellevo.quellevo.web_services.rest_entities;

/**
 * Created by Nicolas on 2/7/2017.
 */

public class AssignItemEventSuccess {
    private EventItem eventItem;
    private Event event;

    public AssignItemEventSuccess(Event event,EventItem eventItem) {
        this.eventItem = eventItem;
        this.event = event;
    }

    public EventItem getEventItem() {
        return eventItem;
    }

    public void setEventItem(EventItem eventItem) {
        this.eventItem = eventItem;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
