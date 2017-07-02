package com.quellevo.quellevo.web_services.rest_entities;

/**
 * Created by Nicolas on 2/7/2017.
 */

public class BuyItemRequestBody {
    private boolean bought;
    private Float cost;

    public BuyItemRequestBody(Float cost) {
        this.cost = cost;
        this.bought = true;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }
}
