package com.quellevo.quellevo.web_services.retrofit;

import com.quellevo.quellevo.web_services.rest_entities.ApiResponse;
import com.quellevo.quellevo.web_services.rest_entities.BooleanDataResponse;
import com.quellevo.quellevo.web_services.rest_entities.CreateEventRequest;
import com.quellevo.quellevo.web_services.rest_entities.CreateLoginEmailRequest;
import com.quellevo.quellevo.web_services.rest_entities.CreateUserRequest;
import com.quellevo.quellevo.web_services.rest_entities.Event;
import com.quellevo.quellevo.web_services.rest_entities.FirebaseRequest;
import com.quellevo.quellevo.web_services.rest_entities.UserEvent;
import com.quellevo.quellevo.web_services.rest_entities.UserEventResponse;
import com.quellevo.quellevo.web_services.rest_entities.UserLoginResponse;
import com.quellevo.quellevo.web_services.rest_entities.UserSignUpInfo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Nicolas on 11/5/2017.
 */

public interface QueLlevoRetrofitEndpoints {
    public static final String HTTP_PREFIX = "https://";
    public static final String IP = "young-falls-29454.herokuapp.com/";
    public static final String BASE_URL = HTTP_PREFIX + IP;

    @POST("users/")
    Call<ApiResponse<UserSignUpInfo>> create(@Body CreateUserRequest userRequest);

    //LOGIN
    @POST("oauth/token")
    Call<UserLoginResponse> login(@Body CreateLoginEmailRequest loginRequest);


    @PUT("user/device")
    Call<BooleanDataResponse> postFirebaseToken(@Body String token);

    @POST("events")
    Call<ApiResponse<Event>> createEvent(@Body CreateEventRequest request);

    @GET("users")
    Call<ApiResponse<ArrayList<UserEvent>>> getAllUsers();

    @GET("events")
    Call<ApiResponse<ArrayList<Event>>> getAllEvents();

    @GET("events/{eventId}")
    Call<ApiResponse<Event>> getEvent(@Path("eventId") Long eventId);

    //TODO ver respuesta
    @DELETE("events/{eventId}/event_items/{eventItemId}")
    Call<ApiResponse<Event>> deleteEvent(@Path("eventId") Long eventId,@Path("eventItemId") Long eventItemId);

    //TODO ver respuesta
    @POST("events/{eventId}/event_items/{eventItemId}/assign")
    Call<ApiResponse<Event>> asignItem(@Path("eventId") Long eventId,@Path("eventItemId") Long eventItemId,@Body Long event_user_id);
}
