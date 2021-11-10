package com.huypham.instagramdemo.data.repository;

import com.huypham.instagramdemo.data.local.prefs.UserPreferences;
import com.huypham.instagramdemo.data.model.User;
import com.huypham.instagramdemo.data.remote.NetworkService;
import com.huypham.instagramdemo.data.remote.Networking;
import com.huypham.instagramdemo.data.remote.request.LoginRequest;
import com.huypham.instagramdemo.data.remote.request.SignUpRequest;
import com.huypham.instagramdemo.data.remote.request.UpdateInfoRequest;
import com.huypham.instagramdemo.data.remote.response.GeneralResponse;
import com.huypham.instagramdemo.data.remote.response.LoginResponse;
import com.huypham.instagramdemo.data.remote.response.SignUpResponse;
import com.huypham.instagramdemo.data.remote.response.UserInfoResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Function;

@Singleton
public class UserRepository {
    private NetworkService networkService;
    private UserPreferences userPreferences;

    @Inject
    public UserRepository(NetworkService networkService, UserPreferences userPreferences) {
        this.networkService = networkService;
        this.userPreferences = userPreferences;
    }

    public void saveCurrentUser(User user) {
        userPreferences.setUserId(user.getId());
        userPreferences.setUserName(user.getName());
        userPreferences.setUserEmail(user.getEmail());
        userPreferences.setAccessToken(user.getAccessToken());
    }

    public void removeCurrentUser() {
        userPreferences.removeUserId();
        userPreferences.removeUserName();
        userPreferences.removeUserEmail();
        userPreferences.removeAccessToken();
    }

    public User getCurrentUser() {
        String userId = userPreferences.getUserId();
        String userName = userPreferences.getUserName();
        String userEmail = userPreferences.getUserEmail();
        String accessToken = userPreferences.getAccessToken();

        if (userId != null && userName != null && userEmail != null && accessToken != null)
            return new User(userId, userName, userEmail, accessToken, null);
        else
            return null;
    }

    public Single<User> doLoginUser(String email, String password) {
        return networkService.doLoginCall(new LoginRequest(email, password), Networking.API_KEY)
                .map(new Function<LoginResponse, User>() {
                    @Override
                    public User apply(LoginResponse loginResponse) throws Throwable {
                        return new User(
                                loginResponse.userId,
                                loginResponse.userName,
                                loginResponse.userEmail,
                                loginResponse.accessToken,
                                loginResponse.profilePicUrl);
                    }
                });
    }

    public Single<User> doSignUpUser(String email, String password, String name) {
        return networkService.doSignUpCall(new SignUpRequest(email, password, name), Networking.API_KEY)
                .map(new Function<SignUpResponse, User>() {
                    @Override
                    public User apply(SignUpResponse signUpResponse) throws Throwable {
                        return new User(
                                signUpResponse.userId,
                                signUpResponse.userName,
                                signUpResponse.userEmail,
                                signUpResponse.accessToken,
                                null);
                    }
                });
    }

    public Single<UserInfoResponse.UserInfo> doFetchUserInfo(User user) {
        return networkService.doFetchUserInfoCall(user.getId(), user.getAccessToken(), Networking.API_KEY)
                .map(new Function<UserInfoResponse, UserInfoResponse.UserInfo>() {
                    @Override
                    public UserInfoResponse.UserInfo apply(UserInfoResponse userInfoResponse) throws Throwable {
                        return userInfoResponse.data;
                    }
                });
    }

    public Single<GeneralResponse> doLogoutUser(User user) {
        return networkService.doLogOutCall(user.getId(), user.getAccessToken(), Networking.API_KEY);
    }

    public Single<GeneralResponse> doUpdateUserInfo(UpdateInfoRequest updateInfo, User user) {
        return networkService.doUpdateUserInfoCall(updateInfo, user.getId(), user.getAccessToken(), Networking.API_KEY);
    }

}
