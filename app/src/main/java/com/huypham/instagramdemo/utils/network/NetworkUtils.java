package com.huypham.instagramdemo.utils.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.huypham.instagramdemo.InstagramApplication;

import java.io.IOException;
import java.net.ConnectException;

import retrofit2.HttpException;

public class NetworkUtils {

    public static final String TAG = "NetworkUtils";

    public static boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) InstagramApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static NetworkError castToNetworkError(Throwable throwable) {
        NetworkError defaultNetworkError = new NetworkError();
        try {
            if (throwable instanceof ConnectException) return new NetworkError(0, "0");
            if (!(throwable instanceof HttpException)) return defaultNetworkError;
            NetworkError error = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create()
                    .fromJson(((HttpException) throwable).response().errorBody().string(), NetworkError.class);
            return new NetworkError(((HttpException) throwable).code(), error.statusCode, error.message);
        } catch (IOException e) {
            Log.d(TAG, e.toString());
        } catch (JsonSyntaxException e) {
            Log.d(TAG, e.toString());
        } catch (NullPointerException e) {
            Log.d(TAG, e.toString());
        }
        return defaultNetworkError;
    }
}
