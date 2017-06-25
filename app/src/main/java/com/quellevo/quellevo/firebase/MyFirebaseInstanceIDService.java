package com.quellevo.quellevo.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.quellevo.quellevo.entities.User;
import com.quellevo.quellevo.events.PostFirebaseTokenEvent;
import com.quellevo.quellevo.web_services.rest_entities.FirebaseRequest;
import com.quellevo.quellevo.web_services.retrofit.RetroiftServiceExecutor;


import org.greenrobot.eventbus.EventBus;


/**
 * Created by Nicolas on 17/1/2017.
 */

//agregar esta clase como service al manifest
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String LOGTAG = "token-refreshed";

    @Override
    public void onTokenRefresh() {
        //Se obtiene el token actualizado
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        if (refreshedToken != null) {
            Log.d(LOGTAG, "Token actualizado: " + refreshedToken);
            //TODO FIREBASE ENVIAR TOKEN A LA WEBAPP PARA QUE LO ASOCIEN AL USER
            User.getInstance().setFirebaseToken(refreshedToken);
            if (User.getInstance().getAccessToken() != null && !User.getInstance().getAccessToken().isEmpty()) {
                RetroiftServiceExecutor.getInstance();
                EventBus.getDefault().post(new PostFirebaseTokenEvent(new FirebaseRequest(refreshedToken)));
            }
        }
    }

    /* Ejemplo JSON de como vienen las notificaciones. La primer parte es siempre igual. la de data es personalizada por el developper
     {
    "to" : "APA91bHun4MxP5egoKMwt2KZFBaFUH-1RYqx...",
    "notification" : {
      "body" : "great match!",
      "title" : "Portugal vs. Denmark",
      "icon" : "myicon"
    },
    "data" : {
      "Nick" : "Mario",
      "Room" : "PortugalVSDenmark"
    }
  }
     */
}
