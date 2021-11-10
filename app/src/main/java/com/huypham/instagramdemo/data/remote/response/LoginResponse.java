package com.huypham.instagramdemo.data.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    public LoginResponse() {
    }

    public LoginResponse(String statusCode, String status, String message, String accessToken, String userId, String userName, String userEmail, String profilePicUrl) {
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
        this.accessToken = accessToken;
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.profilePicUrl = profilePicUrl;
    }

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
    @SerializedName("userId")
    public String userId;

    @Expose
    @SerializedName("userName")
    public String userName;

    @Expose
    @SerializedName("userEmail")
    public String userEmail;

    @Expose
    @SerializedName("profilePicUrl")
    public String profilePicUrl;
}
