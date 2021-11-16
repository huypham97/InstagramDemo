package com.huypham.instagramdemo.data.model;

import java.util.Map;

public class Image {
    public Image(String url, Map<String, String> headers) {
        this.url = url;
        this.headers = headers;
    }

    public Image(String url, Map<String, String> headers, int placeholderWidth, int placeholderHeight) {
        this.url = url;
        this.headers = headers;
        this.placeholderWidth = placeholderWidth;
        this.placeholderHeight = placeholderHeight;
    }

    public String url;

    public Map<String, String> headers;

    public int placeholderWidth = -1;

    public int placeholderHeight = -1;
}
