package com.huypham.instagramdemo.data.remote.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostCreateRequest {

    public PostCreateRequest() {
    }

    public PostCreateRequest(String imgUrl, int imgWidth, int imgHeight) {
        this.imgUrl = imgUrl;
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
    }

    @Expose
    @SerializedName("imgUrl")
    String imgUrl;

    @Expose
    @SerializedName("imgWidth")
    int imgWidth;

    @Expose
    @SerializedName("imgHeight")
    int imgHeight;

}
