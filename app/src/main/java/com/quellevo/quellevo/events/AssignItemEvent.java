package com.quellevo.quellevo.events;

/**
 * Created by Nicolas on 25/6/2017.
 */

public class AssignItemEvent {
    private Long eventId;
    private Long eventItemId;
    private Long eventUserId;

    public AssignItemEvent(Long eventId, Long eventItemId, Long eventUserId) {
        this.eventId = eventId;
        this.eventItemId = eventItemId;
        this.eventUserId = eventUserId;
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

    public Long getEventUserId() {
        return eventUserId;
    }

    public void setEventUserId(Long eventUserId) {
        this.eventUserId = eventUserId;
    }
}
