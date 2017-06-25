package com.quellevo.quellevo.custom_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quellevo.quellevo.R;


/**
 * Created by Nicolas on 9/5/2017.
 */

public class LoginButton extends LinearLayout {
    private TextView textView;
    private ImageView imageView;

    public LoginButton(Context context) {
        this(context,null);
    }

    public LoginButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.login_button,this,true);
        setBackgroundColor(getResources().getColor(R.color.colorAccent));
        setOrientation(HORIZONTAL);
        textView = (TextView) findViewById(R.id.loginButton_text_id);
        imageView = (ImageView) findViewById(R.id.loginButton_image_id);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LoginButton, 0, 0);
        try {
            String text = ta.getString(R.styleable.LoginButton_textButton);
            Drawable image = ta.getDrawable(R.styleable.LoginButton_imageButton);
            textView.setText(text);
            imageView.setImageDrawable(image);
        } finally {
            ta.recycle();
        }
    }
}
