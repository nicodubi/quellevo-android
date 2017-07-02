package com.quellevo.quellevo.web_services.rest_entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Nicolas on 23/6/2017.
 */

public class CreateEventRequest {

    private String name;
    private Date date;
    private ArrayList<UserEvent> userArrayIds;
    private String user_ids;
    private ArrayList<EventItem> userItemsId;
    private String item_ids;

    public CreateEventRequest(String name, Date date) {
        this(name, date, new ArrayList<UserEvent>(), new ArrayList<EventItem>());
    }

    public void setUserArrayIds(ArrayList<UserEvent> userArrayIds) {
        this.userArrayIds = userArrayIds;
    }

    public CreateEventRequest(String name, Date date, ArrayList<UserEvent> userArrayIds, ArrayList<EventItem> eventItems) {
        this.name = name;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.date = format.parse("17/06/2017");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.userArrayIds = userArrayIds;
        this.userItemsId = eventItems;
    }

    public CreateEventRequest(String name, Date date, ArrayList<UserEvent> userArrayIds) {
        this.name = name;
        this.date = date;
        this.userArrayIds = userArrayIds;
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
        String coma = ",";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        for (UserEvent id : userArrayIds) {
            stringBuilder.append(id.getId()).append(coma);
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    public void setUser_ids(String user_ids) {
        this.user_ids = user_ids;
    }

    public String getItem_ids() {
        String coma = ",";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        for (EventItem id : userItemsId) {
            stringBuilder.append(id.getId()).append(coma);
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    public void setItem_ids(String item_ids) {
        this.item_ids = item_ids;
    }

    public void setUserItemsId(ArrayList<EventItem> userItemsId) {
        this.userItemsId = userItemsId;
    }
}
