package com.quellevo.quellevo.web_services.rest_entities;

/**
 * Created by Nicolas on 2/7/2017.
 */

public class UnassingItemEventSuccess {

    private EventItem eventItem;
    private Event event;

    public UnassingItemEventSuccess(Event event, EventItem eventItem) {
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
