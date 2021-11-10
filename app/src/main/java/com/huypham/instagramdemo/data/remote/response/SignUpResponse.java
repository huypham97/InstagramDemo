package com.huypham.instagramdemo.data.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpResponse {
    @Expose
    @SerializedName("statusCode")
    public String statusCode;

    @Expose
    @SerializedName("status")
    public String status;

    @Expose
    @SerializedName("message")
    public String message;

    @Expose
    @SerializedName("accessToken")
    public String accessToken;

    @Expose
    @SerializedName("refreshToken")
    public String refreshToken;

    @Expose
    @SerializedName("userId")
    public String userId;

    @Expose
    @SerializedName("userName")
    public String userName;

    @Expose
    @SerializedName("userEmail")
    public String userEmail;
}
