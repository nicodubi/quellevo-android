package com.quellevo.quellevo.events;

import com.quellevo.quellevo.web_services.rest_entities.BuyItemRequestBody;

/**
 * Created by Nicolas on 2/7/2017.
 */

public class BuyItemEventRequest {
    BuyItemRequestBody requestBody;
    private Long eventItem;

    public BuyItemEventRequest(BuyItemRequestBody requestBody, Long eventItem) {
        this.requestBody = requestBody;
        this.eventItem = eventItem;
    }

    public BuyItemRequestBody getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(BuyItemRequestBody requestBody) {
        this.requestBody = requestBody;
    }

    public Long getEventItem() {
        return eventItem;
    }

    public void setEventItem(Long eventItem) {
        this.eventItem = eventItem;
    }
}
