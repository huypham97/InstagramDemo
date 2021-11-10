package com.huypham.instagramdemo.data.local.prefs;

import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppUserPreferences implements UserPreferences {

    private static String KEY_USER_ID = "PREF_KEY_USER_ID";
    private static String KEY_USER_NAME = "PREF_KEY_USER_NAME";
    private static String KEY_USER_EMAIL = "PREF_KEY_USER_EMAIL";
    private static String KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";

    private final SharedPreferences prefs;

    @Inject
    public AppUserPreferences(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    @Override
    public void setUserId(String userId) {
        prefs.edit().putString(KEY_USER_ID, userId).apply();
    }

    @Override
    public String getUserId() {
        return prefs.getString(KEY_USER_ID, null);
    }

    @Override
    public void removeUserId() {
        prefs.edit().remove(KEY_USER_ID).apply();
    }

    @Override
    public void setUserName(String userName) {
        prefs.edit().putString(KEY_USER_NAME, userName).apply();
    }

    @Override
    public String getUserName() {
        return prefs.getString(KEY_USER_NAME, null);
    }

    @Override
    public void removeUserName() {
        prefs.edit().remove(KEY_USER_NAME).apply();
    }

    @Override
    public void setUserEmail(String email) {
        prefs.edit().putString(KEY_USER_EMAIL, email).apply();
    }

    @Override
    public String getUserEmail() {
        return prefs.getString(KEY_USER_EMAIL, null);
    }

    @Override
    public void removeUserEmail() {
        prefs.edit().remove(KEY_USER_EMAIL).apply();
    }

    @Override
    public void setAccessToken(String token) {
        prefs.edit().putString(KEY_ACCESS_TOKEN, token).apply();
    }

    @Override
    public String getAccessToken() {
        return prefs.getString(KEY_ACCESS_TOKEN, null);
    }

    @Override
    public void removeAccessToken() {
        prefs.edit().remove(KEY_ACCESS_TOKEN).apply();
    }
}
