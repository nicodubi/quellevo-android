package com.quellevo.quellevo.entities;


import com.quellevo.quellevo.web_services.rest_entities.CreateLoginEmailRequest;
import com.quellevo.quellevo.web_services.rest_entities.UserLoginResponse;
import com.quellevo.quellevo.web_services.rest_entities.UserSignUpInfo;

/**
 * Created by Nicolas on 11/5/2017.
 */

public class User {

    private static User instance;
    private UserLoginResponse userLoginResponse;
    private String firebaseToken;
    private UserSignUpInfo userSignUpInfo;
    private boolean appInit;
    private CreateLoginEmailRequest credentials;

    private User() {
        if (userLoginResponse == null) {
            userLoginResponse = new UserLoginResponse();
            userSignUpInfo = new UserSignUpInfo();
        }
    }

    public UserLoginResponse getUserLoginResponse() {
        return userLoginResponse;
    }

    public void setUserLoginResponse(UserLoginResponse userLoginResponse) {
        this.userLoginResponse = userLoginResponse;
    }

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }


    public String getAccessToken() {
        String accessToken = "";
        if (userSignUpInfo != null && userSignUpInfo.getAccess_token() != null) {
            accessToken = userSignUpInfo.getAccess_token();
        }

        if (userLoginResponse != null && userLoginResponse.getAccess_token() != null) {
            accessToken = userLoginResponse.getAccess_token();
        }
        return accessToken;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public UserSignUpInfo getUserSignUpInfo() {
        return userSignUpInfo;
    }

    public void setUserSignUpInfo(UserSignUpInfo userSignUpInfo) {
        this.userSignUpInfo = userSignUpInfo;
    }

    public boolean hasLoginProfile() {
        return (getUserLoginResponse().getAccess_token() != null && !getUserLoginResponse().getAccess_token().isEmpty());
    }

    public boolean hasSignUpProfile() {
        return (getUserSignUpInfo().getAccess_token() != null && !getUserSignUpInfo().getAccess_token().isEmpty());

    }

    public boolean isAppInit() {
        return appInit;
    }

    public void setAppInit(boolean appInit) {
        this.appInit = appInit;
    }

    public CreateLoginEmailRequest getCredentials() {
        return credentials;
    }

    public void setCredentials(CreateLoginEmailRequest credentials) {
        this.credentials = credentials;
    }

    public void cleanInfo() {
        credentials = null;
        userLoginResponse = null;
        userSignUpInfo = null;
        userLoginResponse = new UserLoginResponse();
        userSignUpInfo = new UserSignUpInfo();

    }
}
