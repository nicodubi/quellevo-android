package com.quellevo.quellevo.events;

/**
 * Created by Nicolas on 25/6/2017.
 */

public class DeleteEventRequest {
    private Long eventId;
    private Long eventItemId;

    public DeleteEventRequest(Long eventId, Long eventItemId) {
        this.eventId = eventId;
        this.eventItemId = eventItemId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getEventItemId() {
        return eventItemId;
    }

    public void setEventItemId(Long eventItemId) {
        this.eventItemId = eventItemId;
    }
}
