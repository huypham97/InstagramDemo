package com.huypham.instagramdemo.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    public User() {
    }

    public User(String id, String name, String email, String accessToken, String profilePicUrl) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.accessToken = accessToken;
        this.profilePicUrl = profilePicUrl;
    }

    @Expose
    @SerializedName("userId")
    private String id;

    @Expose
    @SerializedName("userName")
    private String name;

    @Expose
    @SerializedName("userEmail")
    private String email;

    @Expose
    @SerializedName("accessToken")
    private String accessToken;

    @Expose
    @SerializedName("profilePicUrl")
    private String profilePicUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }
}
