package com.quellevo.quellevo.events;

import com.quellevo.quellevo.web_services.rest_entities.EventItem;

import java.util.ArrayList;

/**
 * Created by Nicolas on 29/6/2017.
 */

public class GetEventListSuccess {

    private ArrayList<EventItem> eventItems;

    public GetEventListSuccess(ArrayList<EventItem> eventItems) {
        this.eventItems = eventItems;
    }

    public ArrayList<EventItem> getEventItems() {
        return eventItems;
    }

    public void setEventItems(ArrayList<EventItem> eventItems) {
        this.eventItems = eventItems;
    }
}
