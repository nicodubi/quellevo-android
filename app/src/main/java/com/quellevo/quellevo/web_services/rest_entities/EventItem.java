package com.quellevo.quellevo.web_services.rest_entities;

/**
 * Created by Nicolas on 23/6/2017.
 */

public class EventItem {
    private Long id;
    private String name;
    private Float cost;
    private Boolean bought;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Boolean getBought() {
        return bought;
    }

    public void setBought(Boolean bought) {
        this.bought = bought;
    }
}
