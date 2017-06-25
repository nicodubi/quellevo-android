package com.quellevo.quellevo.web_services.rest_entities;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Nicolas on 23/6/2017.
 */

public class CreateEventRequest {

    private String name;
    private Date date;
    private ArrayList<String> userArrayIds;
    private String user_ids;

    public CreateEventRequest(String name, Date date) {
        this(name, date, new ArrayList<String>());
    }

    public CreateEventRequest(String name, Date date, ArrayList<String> userArrayIds) {
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
        for (String id : userArrayIds) {
            stringBuilder.append(id).append(coma);
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }
}
