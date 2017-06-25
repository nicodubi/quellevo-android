package com.quellevo.quellevo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.quellevo.quellevo.entities.User;
import com.quellevo.quellevo.web_services.rest_entities.CreateLoginEmailRequest;
import com.quellevo.quellevo.web_services.rest_entities.UserLoginResponse;
import com.quellevo.quellevo.web_services.rest_entities.UserSignUpInfo;


import java.util.ArrayList;

/**
 * Created by Nicolas on 11/5/2017.
 */

public class CustomSharedPreferences {
    private SharedPreferences sp;
    private Gson gson;
    private SharedPreferences.Editor editor;

    public CustomSharedPreferences(Context context) {
        sp = context.getSharedPreferences(Constants.SHARED_PREFERENCES, context.MODE_PRIVATE);
        editor = sp.edit();
        gson = new Gson();
    }

    public void saveUser(User user) {
        if (user.hasLoginProfile()) {
            String loginProfileInfo = gson.toJson(user.getUserLoginResponse());
            editor.putString(Constants.USER_PROFILE_LOGIN_SP_KEY, loginProfileInfo);
        } else if (user.hasSignUpProfile()) {
            String signUpProfileInfo = gson.toJson(user.getUserSignUpInfo());
            editor.putString(Constants.USER_PROFILE_SIGNUP_SP_KEY, signUpProfileInfo);
        }
        editor.apply();
    }

    public User getUser() {
        String loginProfileInfo = sp.getString(Constants.USER_PROFILE_LOGIN_SP_KEY, null);
        String signUpProfileInfo = sp.getString(Constants.USER_PROFILE_SIGNUP_SP_KEY, null);
        if (loginProfileInfo != null) {
            User.getInstance().setUserLoginResponse(gson.fromJson(loginProfileInfo, UserLoginResponse.class));
        }
        if (signUpProfileInfo != null) {
            User.getInstance().setUserSignUpInfo(gson.fromJson(signUpProfileInfo, UserSignUpInfo.class));
        }
        return User.getInstance();
    }

    public void removeUserSaved() {
        editor.remove(Constants.USER_PROFILE_LOGIN_SP_KEY);
        editor.remove(Constants.USER_PROFILE_SIGNUP_SP_KEY);
        editor.apply();
    }

    public void saveUserCredentials(CreateLoginEmailRequest createLoginEmailRequest) {
        String info = gson.toJson(createLoginEmailRequest);
        editor.putString(Constants.USER_CREDENTIALS, info).apply();
    }


    public void removeUserCredentials() {
        editor.remove(Constants.USER_CREDENTIALS).apply();
    }


    public CreateLoginEmailRequest getUserCredentials() {
        String info = sp.getString(Constants.USER_CREDENTIALS, null);
        if (info != null) {
            CreateLoginEmailRequest credentials = gson.fromJson(info, CreateLoginEmailRequest.class);
            return credentials;
        } else {
            return null;
        }
    }
}
