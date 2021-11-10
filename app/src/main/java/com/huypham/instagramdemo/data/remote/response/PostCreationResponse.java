package com.huypham.instagramdemo.data.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class PostCreationResponse {
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
    public PostData data;

    public static class PostData {
        @Expose
        @SerializedName("id")
        public String id;

        @Expose
        @SerializedName("imgUrl")
        public String imgUrl;

        @Expose
        @SerializedName("imgWidth")
        public int imgWidth;

        @Expose
        @SerializedName("imgHeight")
        public int imgHeight;

        @Expose
        @SerializedName("createdAt")
        public Date createdAt;
    }
}
