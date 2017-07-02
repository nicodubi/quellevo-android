package com.quellevo.quellevo.web_services.rest_entities;

/**
 * Created by Nicolas on 23/6/2017.
 */

public class EventItem {
    private Long id;
    private String name;
    private Float cost;
    private Boolean bought;
    private Long event_id;
    private Long event_user_id;
    private String created_at;
    private String updated_at;

    public EventItem(String name) {
        this.name = name;
    }

    public EventItem() {
    }

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


    public Long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Long event_id) {
        this.event_id = event_id;
    }

    public Long getEvent_user_id() {
        return event_user_id;
    }

    public void setEvent_user_id(Long event_user_id) {
        this.event_user_id = event_user_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        if (bought != null && bought.booleanValue()) {
            return this.getName() + ":  " + "$" + cost;
        } else {
            return this.getName();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        EventItem other = (EventItem) obj;
        return (id.equals(other.getId()));

    }
}
