package com.huypham.instagramdemo.data.repository;

import com.huypham.instagramdemo.data.model.User;
import com.huypham.instagramdemo.data.remote.NetworkService;
import com.huypham.instagramdemo.data.remote.Networking;
import com.huypham.instagramdemo.data.remote.response.ImageResponse;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PhotoRepository {

    private NetworkService networkService;

    @Inject
    public PhotoRepository(NetworkService networkService) {
        this.networkService = networkService;
    }

    public Single<String> uploadFile(File file, User user) {
        MultipartBody.Part body = MultipartBody.Part.createFormData(
                "image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));

        return networkService.doUploadImageCall(body, user.getId(), user.getAccessToken(), Networking.API_KEY)
                .map(new Function<ImageResponse, String>() {
                    @Override
                    public String apply(ImageResponse imageResponse) throws Throwable {
                        return imageResponse.data.imageUrl;
                    }
                });
    }

}
