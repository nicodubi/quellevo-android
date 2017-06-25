package com.quellevo.quellevo.splash;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.quellevo.quellevo.R;
import com.quellevo.quellevo.entities.User;
import com.quellevo.quellevo.events.KeepLoginEventRequest;
import com.quellevo.quellevo.events.KeepLoginEventResponseError;
import com.quellevo.quellevo.events.LoginUserEventSuccess;
import com.quellevo.quellevo.events.PostFirebaseTokenEvent;
import com.quellevo.quellevo.home.HomeScreenActivity;
import com.quellevo.quellevo.login.LoginActivity;
import com.quellevo.quellevo.utils.AbstractActivity;
import com.quellevo.quellevo.utils.CustomSharedPreferences;
import com.quellevo.quellevo.web_services.rest_entities.CreateLoginEmailRequest;
import com.quellevo.quellevo.web_services.rest_entities.FirebaseRequest;
import com.quellevo.quellevo.web_services.rest_entities.UserLoginResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Nicolas on 9/5/2017.
 */

public class SplashActivity extends AbstractActivity {

    private static final String[] INITIAL_PERMS = {

    };
    private static final int READ_PHONE_STATE_REQUEST = 3;
    private static final int REQUEST_PERMISSION_SETTING = 31;
    private Timer timer;
    private TimerTask timerTask;
    private String tokenFireBase;
    private CreateLoginEmailRequest userCredentials;
    private CustomSharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        User.getInstance().setAppInit(true);
        sharedPreferences = new CustomSharedPreferences(this);
        //TODO descomentar para simular logout del usuario
        //sharedPreferences.removeUserCredentials();
        userCredentials = sharedPreferences.getUserCredentials();
        super.onCreate(savedInstanceState);
        attachRoot(R.layout.splash_activity);
        initFirebase();

    }

    private void initFirebase() {
        tokenFireBase = FirebaseInstanceId.getInstance().getToken();
        if (tokenFireBase != null) {
            //TODO FIREBASE ENVIAR TOKEN A LA WEBAPP PARA QUE LO ASOCIEN AL USER
            Log.d("token firebase splash", tokenFireBase);
            User.getInstance().setFirebaseToken(tokenFireBase);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        startTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
        stopTimer();
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void startTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                checkPermissions();
                stopTimer();
            }
        };
        timer.schedule(timerTask, 2000, 5000); //
    }

    private void checkPermissions() {
        if (checkEveryPermissions() != null) {
            ActivityCompat.requestPermissions(this, INITIAL_PERMS, READ_PHONE_STATE_REQUEST);
        } else {
            checkIfIsFirstTime();
        }
    }

    private String checkEveryPermissions() {
        for (int i = 0; i < INITIAL_PERMS.length; ++i) {
            if (!hasPermission(INITIAL_PERMS[i])) {
                return INITIAL_PERMS[i];
            }
        }
        return null;
    }

    private boolean hasPermission(String perm) {
        return (ContextCompat.checkSelfPermission(this, perm) ==
                PackageManager.PERMISSION_GRANTED);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case READ_PHONE_STATE_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; ++i) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i]);
                            if (showRationale) {
                                ActivityCompat.requestPermissions(this, INITIAL_PERMS, requestCode);
                            } else {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                            }
                            return;
                        }
                    }
                    checkIfIsFirstTime();
                } else {
                    finish();
                }
            }
        }
        // other 'case' lines to check for other
        // permissions this app might request
    }

    private void checkIfIsFirstTime() {
        if (userCredentials == null) {
            //don´t have creentials,go to login
            goToLoginActivity();
        } else {
            User.getInstance().setCredentials(userCredentials);
            EventBus.getDefault().post(new KeepLoginEventRequest(userCredentials));
        }
    }

    @Subscribe
    public void autoLogin(LoginUserEventSuccess event) {
        UserLoginResponse response = event.getLoginResponse();
        User.getInstance().setUserLoginResponse(response);

        //sendFirebaseToken();
        Intent intent = new Intent(this, HomeScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void sendFirebaseToken() {
        if (User.getInstance().getFirebaseToken() != null && !User.getInstance().getFirebaseToken().isEmpty()) {
            EventBus.getDefault().post(new PostFirebaseTokenEvent(new FirebaseRequest(User.getInstance().getFirebaseToken())));
        }
    }

    @Subscribe
    public void autoLoginError(KeepLoginEventResponseError event) {
        goToLoginActivity();
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

