package com.quellevo.quellevo.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quellevo.quellevo.R;
import com.quellevo.quellevo.entities.User;
import com.quellevo.quellevo.events.LoginUserEvent;
import com.quellevo.quellevo.events.LoginUserEventSuccess;
import com.quellevo.quellevo.home.HomeScreenActivity;
import com.quellevo.quellevo.signup.SignUpActivity;
import com.quellevo.quellevo.utils.AbstractActivity;
import com.quellevo.quellevo.utils.Constants;
import com.quellevo.quellevo.utils.CustomSharedPreferences;
import com.quellevo.quellevo.web_services.rest_entities.CreateLoginEmailRequest;
import com.quellevo.quellevo.web_services.rest_entities.UserLoginResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Nicolas on 19/6/2017.
 */

public class LoginEmailActivity extends AbstractActivity {


    private static final int MAX_LENGHT_PASS = 20;
    private static final int MIN_LENGHT_PASS = 8;

    @BindView(R.id.login_email_edittext_id)
    EditText email;
    @BindView(R.id.login_password_edittext_id)
    EditText password;
    @BindView(R.id.login_forgot_password)
    TextView forgotPassword;
    @BindView(R.id.login_sign_in_button)
    TextView signInButton;
    @BindView(R.id.login_dont_have_account_button_id)
    LinearLayout signUp;
    @BindView(R.id.zing_image)
    ImageView image;

    private boolean loginInModalWay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachRoot(R.layout.email_login_activity);
        ButterKnife.bind(this);
        loginInModalWay = getIntent().getBooleanExtra(Constants.EMAIL_LOGIN_MODAL_WAY, false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    @OnClick(R.id.login_sign_in_button)
    public void clickSignIn() {
        if (verificateFormatEmail()) {
            if (true || verificateFormatPassword()) {
                //TODO hay que ver si lleno el email o el username y setear el que haya llenado
                CreateLoginEmailRequest userRequest = new CreateLoginEmailRequest();
                userRequest.setEmail(email.getText().toString());
                userRequest.setPassword(password.getText().toString());
                EventBus.getDefault().post(new LoginUserEvent(userRequest));
                showProgressDialog();
            }
        }
    }

    @Subscribe
    public void onLoginUserSuccess(LoginUserEventSuccess eventSuccess) {
        UserLoginResponse response = eventSuccess.getLoginResponse();
        String userEmailInSharedPref = null;
        if (User.getInstance().getCredentials() != null) {
            userEmailInSharedPref = User.getInstance().getCredentials().getEmail();
        }

        User.getInstance().setUserLoginResponse(response);
        saveCredentials();
        stopDialog();
        if (loginInModalWay && User.getInstance().getCredentials().getEmail().equals(userEmailInSharedPref)) {
            finish();
        } else {
            Intent intent = new Intent(this, HomeScreenActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void saveCredentials() {
        CreateLoginEmailRequest credentials = new CreateLoginEmailRequest();
        credentials.setPassword(password.getText().toString());
        credentials.setEmail(email.getText().toString());
        CustomSharedPreferences shared = new CustomSharedPreferences(this);
        shared.saveUserCredentials(credentials);
        User.getInstance().setCredentials(credentials);
    }

    private boolean verificateFormatPassword() {
        String pass = password.getText().toString();
        if (pass.isEmpty()) {
            Toast.makeText(this, getString(R.string.password_is_empty), Toast.LENGTH_SHORT).show();
            return false;
        }

        StringBuilder msgError = new StringBuilder();
        if (pass.length() > MAX_LENGHT_PASS || pass.length() < MIN_LENGHT_PASS) {
            msgError.append(getString(R.string.password_lenght_error)).append(Constants.lineSkip);
        }
        //check one number
        if (!pass.matches(".*\\d+.*")) {
            msgError.append(getString(R.string.password_must_have_digit_error)).append(Constants.lineSkip);
        }
        //check capital letter
        if (!pass.matches(".*[A-Z].*")) {
            msgError.append(getString(R.string.password_must_have_capital_error)).append(Constants.lineSkip);
        }
        //check lower case
        if (!pass.matches(".*[a-z].*")) {
            msgError.append(getString(R.string.password_must_have_lower_case_error)).append(Constants.lineSkip);
        }

        //check special character
        if (!pass.matches(".*[^A-Za-z0-9].*")) {
            msgError.append(getString(R.string.password_must_have_special_character_error)).append(Constants.lineSkip);
        }

        if (msgError.toString().isEmpty()) {
            return true;
        } else {
            Toast.makeText(this, msgError.toString(), Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private boolean verificateFormatEmail() {
        if (email.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.email_is_empty), Toast.LENGTH_SHORT).show();
            return false;
        }

        String email = this.email.getText().toString();
        //TODO encontrar regex valida
        if (!email.matches(".*[@].*") || !email.matches(".*[\\.com].*")) {
            Toast.makeText(this, getString(R.string.email_invalid_Format), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @OnClick(R.id.login_dont_have_account_button_id)
    public void clickSignUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
