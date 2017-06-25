package com.quellevo.quellevo.events;


import com.quellevo.quellevo.web_services.rest_entities.FirebaseRequest;

/**
 * Created by Nicolas on 14/6/2017.
 */

public class PostFirebaseTokenEvent {

    private FirebaseRequest firebaseRequest;

    public PostFirebaseTokenEvent(FirebaseRequest firebaseRequest) {
        this.firebaseRequest = firebaseRequest;
    }

    public FirebaseRequest getFirebaseRequest() {
        return firebaseRequest;
    }

    public void setFirebaseRequest(FirebaseRequest firebaseRequest) {
        this.firebaseRequest = firebaseRequest;
    }
}
