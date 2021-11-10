package com.huypham.instagramdemo.data.remote.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateInfoRequest {
    @Expose
    @SerializedName("email")
    String name;

    @Expose
    @SerializedName("email")
    String profilePicUrl;

    @Expose
    @SerializedName("email")
    String tagLine;
}
