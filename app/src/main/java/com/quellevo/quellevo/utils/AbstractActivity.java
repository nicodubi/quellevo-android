package com.quellevo.quellevo.utils;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.quellevo.quellevo.R;
import com.quellevo.quellevo.entities.User;
import com.quellevo.quellevo.events.ErrorResponseEvent;
import com.quellevo.quellevo.events.SuccessResponseDefault;
import com.quellevo.quellevo.events.TokenRefreshEventError;
import com.quellevo.quellevo.events.TokenRefreshEventRequest;
import com.quellevo.quellevo.events.TokenRefreshEventSuccess;
import com.quellevo.quellevo.login.LoginActivity;
import com.quellevo.quellevo.web_services.rest_entities.CreateLoginEmailRequest;
import com.quellevo.quellevo.web_services.rest_entities.UserLoginResponse;
import com.quellevo.quellevo.web_services.retrofit.RetroiftServiceExecutor;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.SubscriberExceptionEvent;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Created by Nicolas on 9/5/2017.
 */

public class AbstractActivity extends FragmentActivity {

    private FrameLayout root;
    protected ProgressDialog dialog;

    private boolean openFromNotification;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openFromNotification = getIntent().getBooleanExtra(Constants.OPEN_FROM_NOTIFICATION, false);
        initSingletons();
        verificateUserSingleton();
        setContentView(R.layout.abstract_activity);
        root = (FrameLayout) findViewById(R.id.root_abstract);
        //printHashKey();
    }

    private void printHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getApplicationContext().getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    private void initSingletons() {
        EventBus.getDefault();
        RetroiftServiceExecutor.getInstance();
        User.getInstance();
    }

    protected void attachRoot(int layoutRes) {
        View view = getLayoutInflater().inflate(layoutRes, null, false);
        root.addView(view);
    }

    protected void showProgressDialog() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(false);
            dialog.setTitle(getString(R.string.dialog_waiting));
        }
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    protected void stopDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.cancel();
        }
    }

    @Subscribe
    public void onSuccesssResponseDefaultEvent(SuccessResponseDefault event) {
        stopDialog();
    }

    @Subscribe
    public void onErrorResponseDefaultEvent(ErrorResponseEvent event) {
        stopDialog();
        String errorMsg = (event.getError() == null) ? getString(R.string.default_error_event) : event.getError();
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    protected void attachRoot(View view) {
        root.addView(view);
    }

    protected void registerOnEventBus(Object register) {
        if (!EventBus.getDefault().isRegistered(register)) {
            EventBus.getDefault().register(register);
        }
    }

    protected void unregisterOnEventBus(Object register) {
        if (EventBus.getDefault().isRegistered(register)) {
            EventBus.getDefault().unregister(register);
        }
    }

    protected void verificateUserSingleton() {
        if (!User.getInstance().isAppInit() || ((this.openFromNotification) && User.getInstance().getAccessToken().isEmpty())) {
            Log.d("SINGLETON LOST USER", "Recuperando info usuario...");
            refreshToken();
        }
    }

    private void refreshToken() {
        User.getInstance().setAppInit(true);
        CustomSharedPreferences sharedPreferences = new CustomSharedPreferences(this);
        CreateLoginEmailRequest credentials = sharedPreferences.getUserCredentials();
        if (credentials != null) {
            showProgressDialog();
            User.getInstance().setCredentials(credentials);
            EventBus.getDefault().post(new TokenRefreshEventRequest(credentials));
        }
    }

    @Subscribe
    public void refreshTokenSuccess(TokenRefreshEventSuccess eventSuccess) {
        UserLoginResponse response = eventSuccess.getLoginResponse();
        User.getInstance().setUserLoginResponse(response);
        stopDialog();
    }


    @Subscribe
    public void refreshTokenError(TokenRefreshEventError eventError) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(Constants.EMAIL_LOGIN_MODAL_WAY, true);
        stopDialog();
        startActivity(intent);

    }

    @Subscribe
    public void noSubsrice(SubscriberExceptionEvent exceptionEvent) {
        stopDialog();
    }

}
