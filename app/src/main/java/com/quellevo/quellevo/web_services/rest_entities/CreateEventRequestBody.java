package com.quellevo.quellevo.web_services.rest_entities;

import java.util.Date;

/**
 * Created by Nicolas on 29/6/2017.
 */

public class CreateEventRequestBody {

    private String name;
    private Date date;
    private String user_ids;
    private String item_ids;

    public CreateEventRequestBody(String name, Date date, String user_ids) {
        this.name = name;
        this.date = date;
        this.user_ids = user_ids;
    }

    public CreateEventRequestBody(String name, Date date, String user_ids, String item_ids) {
        this.name = name;
        this.date = date;
        this.user_ids = user_ids;
        this.item_ids = item_ids;
    }

    public CreateEventRequestBody(String name) {
        this.name = name;
    }

    public CreateEventRequestBody(String name, String user_ids) {
        this.name = name;
        this.user_ids = user_ids;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUser_ids() {
        return user_ids;
    }

    public void setUser_ids(String user_ids) {
        this.user_ids = user_ids;
    }

    public String getItem_ids() {
        return item_ids;
    }

    public void setItem_ids(String item_ids) {
        this.item_ids = item_ids;
    }
}
