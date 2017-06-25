package com.quellevo.quellevo.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quellevo.quellevo.R;
import com.quellevo.quellevo.entities.User;
import com.quellevo.quellevo.events.CreateUserEvent;
import com.quellevo.quellevo.events.CreateUserEventSuccess;
import com.quellevo.quellevo.events.LoginUserEvent;
import com.quellevo.quellevo.events.LoginUserEventSuccess;
import com.quellevo.quellevo.home.HomeScreenActivity;
import com.quellevo.quellevo.login.LoginEmailActivity;
import com.quellevo.quellevo.utils.AbstractActivity;
import com.quellevo.quellevo.utils.Constants;
import com.quellevo.quellevo.utils.CustomSharedPreferences;
import com.quellevo.quellevo.web_services.rest_entities.CreateLoginEmailRequest;
import com.quellevo.quellevo.web_services.rest_entities.CreateUserRequest;
import com.quellevo.quellevo.web_services.rest_entities.UserLoginResponse;
import com.quellevo.quellevo.web_services.rest_entities.UserSignUpInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Nicolas on 19/6/2017.
 */

public class SignUpActivity extends AbstractActivity {

    private static final int MAX_LENGHT_PASS = 20;
    private static final int MIN_LENGHT_PASS = 8;
    @BindView(R.id.sign_up_email_edittext_id)
    EditText email;
    @BindView(R.id.sign_up_password_edittext_id)
    EditText password;
    @BindView(R.id.sign_up_name_edittext_id)
    EditText name;
    @BindView(R.id.sign_up_surname_edittext_id)
    EditText surname;
    @BindView(R.id.sign_up_create_account_button_id)
    TextView createAccount;
    @BindView(R.id.sigm_up_terms_checkbox)
    CheckBox termsCheckbox;
    @BindView(R.id.sing_in_already_have_account_button_id)
    LinearLayout signIn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachRoot(R.layout.sign_up_activity);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.sign_up_create_account_button_id)
    public void clickCreateAccount() {
        if (termsCheckbox.isChecked()) {
            if (!name.getText().toString().isEmpty() && !surname.getText().toString().isEmpty()) {
                if (verificateFormatEmail()) {
                    if (verificateFormatPassword()) {
                        CreateUserRequest userRequest = new CreateUserRequest(email.getText().toString(), password.getText().toString()
                        ,name.getText().toString(),surname.getText().toString());
                        userRequest.setNotificationToken(User.getInstance().getFirebaseToken());
                        EventBus.getDefault().post(new CreateUserEvent(userRequest));
                        showProgressDialog();
                    }
                }
            } else {
                Toast.makeText(this, getString(R.string.name_or_surname_empty), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, getString(R.string.must_accept_terms), Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.sing_in_already_have_account_button_id)
    public void clickSignIn() {
        Intent intent = new Intent(this, LoginEmailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Subscribe
    public void onSuccessSignUp(CreateUserEventSuccess event) {
        UserSignUpInfo response = event.getResponse();
        User.getInstance().setUserSignUpInfo(response);
        saveCredentials();
        stopDialog();
        loginToObtainToken();

    }

    private void loginToObtainToken() {
        showProgressDialog();
        CreateLoginEmailRequest userRequest = new CreateLoginEmailRequest();
        userRequest.setEmail(email.getText().toString());
        userRequest.setPassword(password.getText().toString());
        EventBus.getDefault().post(new LoginUserEvent(userRequest));
    }

    private void saveCredentials() {
        CreateLoginEmailRequest credentials = new CreateLoginEmailRequest();
        credentials.setPassword(password.getText().toString());
        credentials.setEmail(email.getText().toString());
        CustomSharedPreferences shared = new CustomSharedPreferences(this);
        shared.saveUserCredentials(credentials);
        User.getInstance().setCredentials(credentials);
    }

    @Subscribe
    public void autoLogin(LoginUserEventSuccess event) {
        UserLoginResponse response = event.getLoginResponse();
        User.getInstance().setUserLoginResponse(response);

        Intent intent = new Intent(this, HomeScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
}
