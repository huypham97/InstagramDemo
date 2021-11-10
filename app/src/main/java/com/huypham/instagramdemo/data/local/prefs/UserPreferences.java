package com.huypham.instagramdemo.data.local.prefs;

public interface UserPreferences {
    void setUserId(String userId);

    String getUserId();

    void removeUserId();

    void setUserName(String userName);

    String getUserName();

    void removeUserName();

    void setUserEmail(String email);

    String getUserEmail();

    void removeUserEmail();

    void setAccessToken(String token);

    String getAccessToken();

    void removeAccessToken();
}
