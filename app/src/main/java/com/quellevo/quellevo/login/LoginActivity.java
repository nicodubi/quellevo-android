package com.quellevo.quellevo.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.quellevo.quellevo.R;
import com.quellevo.quellevo.custom_views.LoginButton;
import com.quellevo.quellevo.signup.SignUpActivity;
import com.quellevo.quellevo.utils.AbstractActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Nicolas on 19/6/2017.
 */

public class LoginActivity extends AbstractActivity {

    @BindView(R.id.login_button_facebook)
    LoginButton facebookButton;

    @BindView(R.id.login_button_email)
    LoginButton emailButton;

    @BindView(R.id.create_account_button_id)
    LinearLayout createAccount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachRoot(R.layout.login_activity);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.login_button_email)
    public void clickEmailLogin() {
        Intent intent = new Intent(this, LoginEmailActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.create_account_button_id)
    public void clickCreateAccount() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

}
