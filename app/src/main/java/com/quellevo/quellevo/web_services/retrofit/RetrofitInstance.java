package com.quellevo.quellevo.web_services.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quellevo.quellevo.utils.DateDeserializer;

import java.util.Date;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nicolas on 11/5/2017.
 */

public class RetrofitInstance {
    private QueLlevoRetrofitEndpoints service;

    public RetrofitInstance() {
        //para agregar interceptor para que en cada request que se haga ya esten los HEADERS
        OkHttpClient clientWithInterceptor = new OkHttpClient.Builder().addInterceptor(new HeaderInterceptor()).build();
        GsonBuilder gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(QueLlevoRetrofitEndpoints.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson.create()))
                .client(clientWithInterceptor)
                .build();

        this.service = retrofit.create(QueLlevoRetrofitEndpoints.class);
    }

    public QueLlevoRetrofitEndpoints getService() {
        return service;
    }
}
