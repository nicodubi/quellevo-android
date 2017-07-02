package com.quellevo.quellevo.web_services.rest_entities;

/**
 * Created by Nicolas on 1/7/2017.
 */

public class UpdateEventRequest {
    private CreateEventRequest eventRequest;
    private Long eventId;

    public UpdateEventRequest(CreateEventRequest eventRequest, Long eventId) {
        this.eventRequest = eventRequest;
        this.eventId = eventId;
    }

    public CreateEventRequest getEventRequest() {
        return eventRequest;
    }

    public void setEventRequest(CreateEventRequest eventRequest) {
        this.eventRequest = eventRequest;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
