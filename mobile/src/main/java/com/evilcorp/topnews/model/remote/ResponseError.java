package com.evilcorp.topnews.model.remote;

/**
 * Created by hristo.stoyanov on 18-Apr-17.
 */

public class ResponseError {
    private String status;
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
