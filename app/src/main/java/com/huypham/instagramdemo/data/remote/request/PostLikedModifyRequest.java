package com.huypham.instagramdemo.data.remote.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostLikedModifyRequest {
    public PostLikedModifyRequest() {
    }

    public PostLikedModifyRequest(String postId) {
        this.postId = postId;
    }

    @Expose
    @SerializedName("postId")
    public String postId;
}
