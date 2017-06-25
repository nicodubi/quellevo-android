package com.quellevo.quellevo.events;

/**
 * Created by Nicolas on 11/5/2017.
 */

public class ErrorResponseEvent {

    private String error;

    public ErrorResponseEvent(String error) {
        this.error = error;
    }

    public ErrorResponseEvent() {
        error = null;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
