package com.huypham.instagramdemo.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Post {

    public Post() {
    }

    public Post(String id, String imgUrl, int imgWidth, int imgHeight, User creator, List<User> likedBy, Date createdAt) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
        this.creator = creator;
        this.likedBy = likedBy;
        this.createdAt = createdAt;
    }

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
    @SerializedName("user")
    public User creator;

    @Expose
    @SerializedName("likedBy")
    public List<User> likedBy;

    @Expose
    @SerializedName("createdAt")
    public Date createdAt;

    public static class User {
        public User() {
        }

        public User(String id, String name, String profilePicUrl) {
            this.id = id;
            this.name = name;
            this.profilePicUrl = profilePicUrl;
        }

        @Expose
        @SerializedName("id")
        public String id;

        @Expose
        @SerializedName("name")
        public String name;

        @Expose
        @SerializedName("profilePicUrl")
        public String profilePicUrl;
    }
}
