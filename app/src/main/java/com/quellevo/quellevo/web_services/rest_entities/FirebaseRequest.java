package com.quellevo.quellevo.web_services.rest_entities;


import com.quellevo.quellevo.utils.Constants;

/**
 * Created by Nicolas on 14/6/2017.
 */

public class FirebaseRequest {

    private int DeviceType;
    private String DeviceId;
    private String NotificationToken;

    public FirebaseRequest(String notificationToken) {
        this();
        NotificationToken = notificationToken;
    }

    private FirebaseRequest() {
        DeviceType = Integer.valueOf(Constants.DEVICE_TYPE_ANDROID);
    }

    public int getDeviceType() {
        return DeviceType;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getNotificationToken() {
        return NotificationToken;
    }

    public void setNotificationToken(String notificationToken) {
        NotificationToken = notificationToken;
    }
}
