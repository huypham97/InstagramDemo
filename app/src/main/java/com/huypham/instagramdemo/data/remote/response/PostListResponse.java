package com.huypham.instagramdemo.data.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.huypham.instagramdemo.data.model.Post;

import java.util.List;

public class PostListResponse {
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
    public List<Post> data;
}
