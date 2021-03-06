package com.quellevo.quellevo.web_services.retrofit;


import com.quellevo.quellevo.entities.User;
import com.quellevo.quellevo.events.AssignItemEvent;
import com.quellevo.quellevo.events.BuyItemEventRequest;
import com.quellevo.quellevo.events.CreateEventSuccess;
import com.quellevo.quellevo.events.CreateUserEvent;
import com.quellevo.quellevo.events.CreateUserEventSuccess;
import com.quellevo.quellevo.events.DeleteEventRequest;
import com.quellevo.quellevo.events.DeleteEventItemSuccess;
import com.quellevo.quellevo.events.ErrorResponseEvent;
import com.quellevo.quellevo.events.GetAllEvents;
import com.quellevo.quellevo.events.GetAllEventsSuccess;
import com.quellevo.quellevo.events.GetAllUsersEvent;
import com.quellevo.quellevo.events.GetAllUsersEventSuccess;
import com.quellevo.quellevo.events.GetAnEvent;
import com.quellevo.quellevo.events.GetAnEventSuccess;
import com.quellevo.quellevo.events.GetEventListSuccess;
import com.quellevo.quellevo.events.GetItemsList;
import com.quellevo.quellevo.events.KeepLoginEventRequest;
import com.quellevo.quellevo.events.KeepLoginEventResponseError;
import com.quellevo.quellevo.events.LoginUserEvent;
import com.quellevo.quellevo.events.LoginUserEventSuccess;
import com.quellevo.quellevo.events.PostFirebaseTokenEvent;
import com.quellevo.quellevo.events.TokenRefreshEventError;
import com.quellevo.quellevo.events.TokenRefreshEventRequest;
import com.quellevo.quellevo.events.TokenRefreshEventSuccess;
import com.quellevo.quellevo.events.UnassignItemEvent;
import com.quellevo.quellevo.events.UpdateEventRequestSuccess;
import com.quellevo.quellevo.web_services.rest_entities.ApiResponse;
import com.quellevo.quellevo.web_services.rest_entities.AssignItemEventSuccess;
import com.quellevo.quellevo.web_services.rest_entities.CreateEventRequest;
import com.quellevo.quellevo.web_services.rest_entities.CreateEventRequestBody;
import com.quellevo.quellevo.web_services.rest_entities.Event;
import com.quellevo.quellevo.web_services.rest_entities.EventItem;
import com.quellevo.quellevo.web_services.rest_entities.FirebaseBody;
import com.quellevo.quellevo.web_services.rest_entities.UnassingItemEventSuccess;
import com.quellevo.quellevo.web_services.rest_entities.UpdateEventRequest;
import com.quellevo.quellevo.web_services.rest_entities.UserEvent;
import com.quellevo.quellevo.web_services.rest_entities.UserLoginResponse;
import com.quellevo.quellevo.web_services.rest_entities.UserSignUpInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nicolas on 11/5/2017.
 */

public class RetroiftServiceExecutor {

    private static RetroiftServiceExecutor instance;
    private RetrofitInstance retrofitInstance;

    private RetroiftServiceExecutor() {
        retrofitInstance = new RetrofitInstance();
        EventBus.getDefault().register(this);
    }

    public static RetroiftServiceExecutor getInstance() {
        if (instance == null) {
            instance = new RetroiftServiceExecutor();
        }
        return instance;
    }

    @Subscribe
    public void createUserRegistration(final CreateUserEvent event) {
        Call<ApiResponse<UserSignUpInfo>> call = retrofitInstance.getService().create(event.getRequest());
        call.enqueue(new Callback<ApiResponse<UserSignUpInfo>>() {
            @Override
            public void onResponse(Call<ApiResponse<UserSignUpInfo>> call, Response<ApiResponse<UserSignUpInfo>> response) {
                if (response.body() != null) {
                    sendResponseFromApiResponse(response, new CreateUserEventSuccess(response.body().getResponse()), null);
                } else {
                    verificateToken(response.code(), event, null);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<UserSignUpInfo>> call, Throwable t) {
                sendDefaultError();
            }
        });
    }

    private <T> void sendResponseFromApiResponse(Response<ApiResponse<T>> response, Object eventSuccess, String msgError) {
        ApiResponse<T> responseBody = response.body();
        if (response.isSuccessful() && responseBody.getResponse() != null) {
            if (responseBody.getResponse() != null) {
                EventBus.getDefault().post(eventSuccess);
            } else {
                //TODO ACA HABRA QUE PASARLE EL ATRIBUTO DE LA RESPONSE msgError CUANDO VENGA POR POSTMAN por ahora se pasa por parametro y se pregunta si es null, despues sacar parametro y el if else
                if (msgError != null) {
                    EventBus.getDefault().post(new ErrorResponseEvent(msgError));
                } else {
                    EventBus.getDefault().post(new ErrorResponseEvent());
                }
                // EventBus.getDefault().post(new ErrorResponseEvent(responseBody.getMsgError()));
            }
        } else {
            sendDefaultError();
        }
    }

    private void sendDefaultError() {
        EventBus.getDefault().post(new ErrorResponseEvent());

    }

    private void sendDefaultError(String errorMsg) {
        EventBus.getDefault().post(new ErrorResponseEvent(errorMsg));

    }

    private void sendResponse(Response response, Object eventSuccess) {
        if (response.isSuccessful() && response.body() != null) {
            EventBus.getDefault().post(eventSuccess);
        } else {
            //TODO ACA HABRA QUE PASARLE EL ATRIBUTO DE LA RESPONSE msgError CUANDO VENGA POR POSTMAN
            sendDefaultError();
            // EventBus.getDefault().post(new ErrorResponseEvent(responseBody.getMsgError()));
        }
    }

    private void sendResponse(Response response, Object eventSuccess, String msgError) {
        if (response.isSuccessful() && response.body() != null) {
            EventBus.getDefault().post(eventSuccess);
        } else {
            if (msgError != null) {
                EventBus.getDefault().post(new ErrorResponseEvent(msgError));
            } else {
                sendDefaultError();
            }
        }
    }


    @Subscribe
    public void loginUser(LoginUserEvent event) {
        Call<UserLoginResponse> call = retrofitInstance.getService().login(event.getRequest());
        call.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                if (response.body() != null) {
                    sendResponse(response, new LoginUserEventSuccess(response.body()), "Email or Password incorrect. Try again");
                } else if (response.code() == 401) {
                    sendDefaultError("Email or Password incorrect. Try again");
                } else {
                    sendDefaultError();
                }
            }

            @Override
            public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                sendDefaultError();
            }
        });
    }

    @Subscribe
    public void keepLoginRequest(KeepLoginEventRequest event) {
        Call<UserLoginResponse> call = retrofitInstance.getService().login(event.getRequest());
        call.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                if (response.body() != null) {
                    sendResponse(response, new LoginUserEventSuccess(response.body()));
                } else {
                    EventBus.getDefault().post(new KeepLoginEventResponseError());
                }
            }

            @Override
            public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                EventBus.getDefault().post(new KeepLoginEventResponseError());
            }
        });
    }

    @Subscribe
    public void refreshTokenRequest(TokenRefreshEventRequest event) {
        Call<UserLoginResponse> call = retrofitInstance.getService().login(event.getRequest());
        call.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                if (response.body() != null) {
                    sendResponse(response, new TokenRefreshEventSuccess(response.body()));
                } else {
                    EventBus.getDefault().post(new TokenRefreshEventError());
                }
            }

            @Override
            public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                EventBus.getDefault().post(new TokenRefreshEventError());
            }
        });
    }

    private void verificateToken(int code, final Object event, Object errorEvent) {
        if (code == 401) {
            Call<UserLoginResponse> call = retrofitInstance.getService().login(User.getInstance().getCredentials());
            call.enqueue(new Callback<UserLoginResponse>() {
                @Override
                public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                    if (response.body() != null && response.isSuccessful()) {
                        User.getInstance().setUserLoginResponse(response.body());
                        EventBus.getDefault().post(event);
                    } else {
                        EventBus.getDefault().post(new TokenRefreshEventError());
                    }
                }

                @Override
                public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                    sendDefaultError();
                }
            });
        } else if (errorEvent == null) {
            sendDefaultError();
        } else {
            EventBus.getDefault().post(errorEvent);
        }
    }


    @Subscribe
    public void postFirebaseToken(final PostFirebaseTokenEvent event) {
        Call<ApiResponse<UserEvent>> call = retrofitInstance.getService().postFirebaseToken(new FirebaseBody(event.getFirebaseRequest().getNotificationToken()));
        call.enqueue(new Callback<ApiResponse<UserEvent>>() {
            @Override
            public void onResponse(Call<ApiResponse<UserEvent>> call, Response<ApiResponse<UserEvent>> response) {
                if (response.body() == null) {
                    //TODO ver si falla cuando el token es enviado por el service de firebase refresh porque se mandaria un default error mas abajo y no habria AbstractAcivity corriendo para recibirlo
                    verificateToken(response.code(), event, null);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<UserEvent>> call, Throwable t) {
                sendDefaultError();
            }
        });
    }

    @Subscribe
    public void createEvent(final CreateEventRequest event) {
        Call<ApiResponse<Event>> call = retrofitInstance.getService().createEvent(new CreateEventRequestBody(event.getName(), event.getDate(), event.getUser_ids(), event.getItem_ids()));
        call.enqueue(new Callback<ApiResponse<Event>>() {
            @Override
            public void onResponse(Call<ApiResponse<Event>> call, Response<ApiResponse<Event>> response) {
                if (response.body() != null) {
                    sendResponseFromApiResponse(response, new CreateEventSuccess(response.body().getResponse()), null);
                } else {
                    verificateToken(response.code(), event, null);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Event>> call, Throwable t) {
                sendDefaultError();
            }
        });
    }

    @Subscribe
    public void getAllUsers(final GetAllUsersEvent event) {
        Call<ApiResponse<ArrayList<UserEvent>>> call = retrofitInstance.getService().getAllUsers();
        call.enqueue(new Callback<ApiResponse<ArrayList<UserEvent>>>() {
            @Override
            public void onResponse(Call<ApiResponse<ArrayList<UserEvent>>> call, Response<ApiResponse<ArrayList<UserEvent>>> response) {
                if (response.body() != null) {
                    sendResponseFromApiResponse(response, new GetAllUsersEventSuccess(response.body().getResponse()), null);
                } else {
                    verificateToken(response.code(), event, null);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<ArrayList<UserEvent>>> call, Throwable t) {
                sendDefaultError();
            }
        });
    }

    @Subscribe
    public void getAllEvents(final GetAllEvents event) {
        Call<ApiResponse<ArrayList<Event>>> call = retrofitInstance.getService().getAllEvents();
        call.enqueue(new Callback<ApiResponse<ArrayList<Event>>>() {
            @Override
            public void onResponse(Call<ApiResponse<ArrayList<Event>>> call, Response<ApiResponse<ArrayList<Event>>> response) {
                if (response.body() != null) {
                    sendResponseFromApiResponse(response, new GetAllEventsSuccess(response.body().getResponse()), null);
                } else {
                    verificateToken(response.code(), event, null);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<ArrayList<Event>>> call, Throwable t) {
                sendDefaultError();
            }
        });
    }

    @Subscribe
    public void getAnEvent(final GetAnEvent event) {
        Call<ApiResponse<Event>> call = retrofitInstance.getService().getEvent(event.getEventId());
        call.enqueue(new Callback<ApiResponse<Event>>() {
            @Override
            public void onResponse(Call<ApiResponse<Event>> call, Response<ApiResponse<Event>> response) {
                if (response.body() != null) {
                    sendResponseFromApiResponse(response, new GetAnEventSuccess(response.body().getResponse()), null);
                } else {
                    verificateToken(response.code(), event, null);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Event>> call, Throwable t) {
                sendDefaultError();
            }
        });
    }

    @Subscribe
    public void deleteEventItem(final DeleteEventRequest event) {
        Call<ApiResponse<Event>> call = retrofitInstance.getService().deleteEvent(event.getEventId(), event.getEventItemId());
        call.enqueue(new Callback<ApiResponse<Event>>() {
            @Override
            public void onResponse(Call<ApiResponse<Event>> call, Response<ApiResponse<Event>> response) {
                if (response.body() != null) {
                    sendResponseFromApiResponse(response, new DeleteEventItemSuccess(), null);
                } else {
                    verificateToken(response.code(), event, null);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Event>> call, Throwable t) {
                sendDefaultError();
            }
        });
    }

    @Subscribe
    public void assignItem(final AssignItemEvent event) {
        Call<ApiResponse<Event>> call = retrofitInstance.getService().asignItem(event.getEventItem().getEvent_id(), event.getEventItem().getId(), event.getAssignItemBody());
        call.enqueue(new Callback<ApiResponse<Event>>() {
            @Override
            public void onResponse(Call<ApiResponse<Event>> call, Response<ApiResponse<Event>> response) {
                if (response.body() != null) {
                    //TODO ver como es la respuesdta de asigan item
                    sendResponseFromApiResponse(response, new AssignItemEventSuccess(response.body().getResponse(), event.getEventItem()), null);
                } else {
                    verificateToken(response.code(), event, null);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Event>> call, Throwable t) {
                sendDefaultError();
            }
        });
    }

    @Subscribe
    public void unassignItem(final UnassignItemEvent event) {
        Call<ApiResponse<Event>> call = retrofitInstance.getService().unasignItem(event.getEventItem().getEvent_id(), event.getEventItem().getId());
        call.enqueue(new Callback<ApiResponse<Event>>() {
            @Override
            public void onResponse(Call<ApiResponse<Event>> call, Response<ApiResponse<Event>> response) {
                if (response.body() != null) {
                    //TODO ver como es la respuesdta de asigan item
                    sendResponseFromApiResponse(response, new UnassingItemEventSuccess(response.body().getResponse(), event.getEventItem()), null);
                } else {
                    verificateToken(response.code(), event, null);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Event>> call, Throwable t) {
                sendDefaultError();
            }
        });
    }

    @Subscribe
    public void updateEventInfo(final UpdateEventRequest event) {
        CreateEventRequestBody body = new CreateEventRequestBody(event.getEventRequest().getName(), event.getEventRequest().getDate()
                , event.getEventRequest().getUser_ids(), event.getEventRequest().getItem_ids());
        Call<ApiResponse<Event>> call = retrofitInstance.getService().updateEventInfo(event.getEventId(), body);
        call.enqueue(new Callback<ApiResponse<Event>>() {
            @Override
            public void onResponse(Call<ApiResponse<Event>> call, Response<ApiResponse<Event>> response) {
                if (response.body() != null) {
                    sendResponseFromApiResponse(response, new UpdateEventRequestSuccess(response.body().getResponse()), null);
                } else {
                    verificateToken(response.code(), event, null);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Event>> call, Throwable t) {
                sendDefaultError();
            }
        });
    }


    @Subscribe
    public void getItemList(final GetItemsList event) {
        Call<ApiResponse<ArrayList<EventItem>>> call = retrofitInstance.getService().getItemsList();
        call.enqueue(new Callback<ApiResponse<ArrayList<EventItem>>>() {
            @Override
            public void onResponse(Call<ApiResponse<ArrayList<EventItem>>> call, Response<ApiResponse<ArrayList<EventItem>>> response) {
                if (response.body() != null) {
                    sendResponseFromApiResponse(response, new GetEventListSuccess(response.body().getResponse()), null);
                } else {
                    verificateToken(response.code(), event, null);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<ArrayList<EventItem>>> call, Throwable t) {
                sendDefaultError();
            }
        });
    }


    @Subscribe
    public void buyItem(final BuyItemEventRequest event) {
        Call<ApiResponse<EventItem>> call = retrofitInstance.getService().buyItem(event.getEventItem(), event.getRequestBody());
        call.enqueue(new Callback<ApiResponse<EventItem>>() {
            @Override
            public void onResponse(Call<ApiResponse<EventItem>> call, Response<ApiResponse<EventItem>> response) {
                if (response.body() != null && response.isSuccessful()) {
                    // EventBus.getDefault().post(new BuyItemEventSuccess());
                } else {
                    verificateToken(response.code(), event, null);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<EventItem>> call, Throwable t) {
                sendDefaultError();
            }
        });
    }

}
