package com.huypham.instagramdemo.utils.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NetworkError {

    public NetworkError() {
    }

    public NetworkError(int status, String statusCode) {
        this.status = status;
        this.statusCode = statusCode;
    }

    public NetworkError(int status, String statusCode, String message) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
    }

    int status = -1;

    @Expose
    @SerializedName("statusCode")
    String statusCode = "-1";

    @Expose
    @SerializedName("message")
    String message = "Something went wrong";

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
