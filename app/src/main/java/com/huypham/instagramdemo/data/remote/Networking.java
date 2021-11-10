package com.huypham.instagramdemo.data.remote;

import com.huypham.instagramdemo.BuildConfig;

import java.io.File;
import java.util.concurrent.TimeUnit;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Networking {
    public static final String HEADER_API_KEY = "x-api-key";
    public static final String HEADER_ACCESS_TOKEN = "x-access-token";
    public static final String HEADER_USER_ID = "x-user-id";

    private static final int NETWORK_CALL_TIMEOUT = 60;
    public static String API_KEY = null;

    public static NetworkService create(String apiKey, String baseUrl, File cacheDir, Long cacheSize) {
        API_KEY = apiKey;

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(new OkHttpClient.Builder()
                        .cache(new Cache(cacheDir, cacheSize))
                        .addInterceptor(httpLoggingInterceptor)
                        .readTimeout(Long.valueOf(NETWORK_CALL_TIMEOUT), TimeUnit.SECONDS)
                        .writeTimeout(Long.valueOf(NETWORK_CALL_TIMEOUT), TimeUnit.SECONDS)
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(NetworkService.class);
    }
}
