package com.quellevo.quellevo.web_services.retrofit;


import com.quellevo.quellevo.entities.User;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Nicolas on 11/5/2017.
 */

public class HeaderInterceptor implements Interceptor {
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";
    public static final String AUTHOTIZATION = "Authorization";
    public static final String BEARER = "Bearer ";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String accessToken = User.getInstance().getAccessToken();
        if (needAuthorization()) {
            request = request.newBuilder()
                    .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                    .addHeader(AUTHOTIZATION, BEARER + accessToken).build();
        } else {
            request = request.newBuilder().addHeader(CONTENT_TYPE, APPLICATION_JSON).build();
        }

        Response response = chain.proceed(request);
        return response;
    }

    private boolean needAuthorization() {
        //TODO
        return true;
    }


//TODO EXAMPLE
    /*@Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .addHeader("appid", "hello")
                .addHeader("deviceplatform", "android")
                .removeHeader("User-Agent")
                .addHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:38.0) Gecko/20100101 Firefox/38.0")
                .build();
        Response response = chain.proceed(request);
        return response;
    }*/
}


