package com.quellevo.quellevo.events;

import com.quellevo.quellevo.web_services.rest_entities.AssignItemBody;
import com.quellevo.quellevo.web_services.rest_entities.EventItem;

/**
 * Created by Nicolas on 25/6/2017.
 */

public class AssignItemEvent {
    private EventItem eventItem;
    private AssignItemBody assignItemBody;

    public AssignItemEvent(EventItem eventItem, AssignItemBody assignItemBody) {
        this.eventItem = eventItem;
        this.assignItemBody = assignItemBody;
    }

    public EventItem getEventItem() {
        return eventItem;
    }

    public void setEventItem(EventItem eventItem) {
        this.eventItem = eventItem;
    }


    public AssignItemBody getAssignItemBody() {
        return assignItemBody;
    }

    public void setAssignItemBody(AssignItemBody assignItemBody) {
        this.assignItemBody = assignItemBody;
    }
}
