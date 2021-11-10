package com.huypham.instagramdemo.data.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeneralResponse {
    @Expose
    @SerializedName("statusCode")
    String statusCode;

    @Expose
    @SerializedName("message")
    String message;
}
