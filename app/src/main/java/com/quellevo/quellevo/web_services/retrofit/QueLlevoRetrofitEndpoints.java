package com.quellevo.quellevo.web_services.retrofit;

import com.quellevo.quellevo.web_services.rest_entities.ApiResponse;
import com.quellevo.quellevo.web_services.rest_entities.AssignItemBody;
import com.quellevo.quellevo.web_services.rest_entities.BuyItemRequestBody;
import com.quellevo.quellevo.web_services.rest_entities.CreateEventRequestBody;
import com.quellevo.quellevo.web_services.rest_entities.CreateLoginEmailRequest;
import com.quellevo.quellevo.web_services.rest_entities.CreateUserRequest;
import com.quellevo.quellevo.web_services.rest_entities.Event;
import com.quellevo.quellevo.web_services.rest_entities.EventItem;
import com.quellevo.quellevo.web_services.rest_entities.FirebaseBody;
import com.quellevo.quellevo.web_services.rest_entities.UserEvent;
import com.quellevo.quellevo.web_services.rest_entities.UserLoginResponse;
import com.quellevo.quellevo.web_services.rest_entities.UserSignUpInfo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
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


    @POST("users/update_token")
    Call<ApiResponse<UserEvent>> postFirebaseToken(@Body FirebaseBody token);

    @POST("events")
    Call<ApiResponse<Event>> createEvent(@Body CreateEventRequestBody request);

    @GET("users")
    Call<ApiResponse<ArrayList<UserEvent>>> getAllUsers();

    @GET("events")
    Call<ApiResponse<ArrayList<Event>>> getAllEvents();

    @GET("events/{eventId}")
    Call<ApiResponse<Event>> getEvent(@Path("eventId") Long eventId);

    //TODO ver respuesta
    @DELETE("events/{eventId}/event_items/{eventItemId}")
    Call<ApiResponse<Event>> deleteEvent(@Path("eventId") Long eventId, @Path("eventItemId") Long eventItemId);

    @POST("events/{eventId}/event_items/{eventItemId}/assign")
    Call<ApiResponse<Event>> asignItem(@Path("eventId") Long eventId, @Path("eventItemId") Long eventItemId, @Body AssignItemBody body);


    @POST("events/{eventId}/event_items/{eventItemId}/assign")
    Call<ApiResponse<Event>> unasignItem(@Path("eventId") Long eventId, @Path("eventItemId") Long eventItemId);

    @POST("events/{eventId}/update")
    Call<ApiResponse<Event>> updateEventInfo(@Path("eventId") Long eventUserId, @Body CreateEventRequestBody event);

    @GET("items")
    Call<ApiResponse<ArrayList<EventItem>>> getItemsList();

    @DELETE("events/{eventId}/event_items/{eventItemId}")
    Call<ApiResponse<Event>> deleteItemFromEvent(@Path("eventId") Long eventId, @Path("eventItemId") Long eventItemId);

    @POST("event_items/{eventItemId}/buy_item")
    Call<ApiResponse<EventItem>> buyItem(@Path("eventItemId") Long eventItemId, @Body BuyItemRequestBody event);
}
