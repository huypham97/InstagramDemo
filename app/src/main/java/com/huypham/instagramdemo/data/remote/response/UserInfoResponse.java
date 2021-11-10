package com.huypham.instagramdemo.data.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfoResponse {
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
    @SerializedName("data")
    public UserInfo data;

    public static class UserInfo {
        @Expose
        @SerializedName("id")
        public String id;

        @Expose
        @SerializedName("name")
        public String name;

        @Expose
        @SerializedName("profilePicUrl")
        public String profilePicUrl;

        @Expose
        @SerializedName("tagLine")
        public String tagLine;
    }
}
