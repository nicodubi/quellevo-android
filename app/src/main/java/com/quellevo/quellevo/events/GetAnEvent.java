package com.quellevo.quellevo.events;

/**
 * Created by Nicolas on 25/6/2017.
 */

public class GetAnEvent {
    private Long eventId;

    public GetAnEvent(Long eventId) {
        this.eventId = eventId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
