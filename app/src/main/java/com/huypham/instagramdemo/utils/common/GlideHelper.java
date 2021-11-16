package com.huypham.instagramdemo.utils.common;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

import java.util.Map;

public class GlideHelper {

    public static GlideUrl getProtectedUrl(String url, Map<String, String> headers) {
        LazyHeaders.Builder builder = new LazyHeaders.Builder();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }
        return new GlideUrl(url, builder.build());
    }

}
