package com.quellevo.quellevo.web_services.rest_entities;

import com.google.gson.annotations.SerializedName;
import com.quellevo.quellevo.utils.Constants;

/**
 * Created by Nicolas on 11/5/2017.
 */

public class CreateUserRequest {

    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    private String name;
    private String surname;

    private String deviceType;
    private String deviceId;
    private String notificationToken;

    public CreateUserRequest(String email, String password) {
        this.email = email;
        this.password = password;
        this.deviceType = Constants.DEVICE_TYPE_ANDROID;
    }

    public CreateUserRequest(String email, String password, String name, String surname) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getNotificationToken() {
        return notificationToken;
    }

    public void setNotificationToken(String notificationToken) {
        this.notificationToken = notificationToken;
    }
}
