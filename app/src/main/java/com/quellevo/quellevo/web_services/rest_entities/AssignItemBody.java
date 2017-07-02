package com.quellevo.quellevo.web_services.rest_entities;

/**
 * Created by Nicolas on 2/7/2017.
 */

public class AssignItemBody {
    private Long event_user_id;

    public AssignItemBody(Long event_user_id) {
        this.event_user_id = event_user_id;
    }

    public Long getEvent_user_id() {
        return event_user_id;
    }

    public void setEvent_user_id(Long event_user_id) {
        this.event_user_id = event_user_id;
    }
}
