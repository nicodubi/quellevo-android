package com.quellevo.quellevo.events;

import com.quellevo.quellevo.web_services.rest_entities.EventItem;

/**
 * Created by Nicolas on 2/7/2017.
 */

public class UnassignItemEvent {
    private EventItem eventItem;

    public UnassignItemEvent(EventItem eventItem) {
        this.eventItem = eventItem;
    }

    public EventItem getEventItem() {
        return eventItem;
    }

    public void setEventItem(EventItem eventItem) {
        this.eventItem = eventItem;
    }
}
