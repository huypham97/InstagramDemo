package com.huypham.instagramdemo.data.remote;

import com.huypham.instagramdemo.data.remote.request.LoginRequest;
import com.huypham.instagramdemo.data.remote.request.PostCreateRequest;
import com.huypham.instagramdemo.data.remote.request.PostLikedModifyRequest;
import com.huypham.instagramdemo.data.remote.request.SignUpRequest;
import com.huypham.instagramdemo.data.remote.request.UpdateInfoRequest;
import com.huypham.instagramdemo.data.remote.response.GeneralResponse;
import com.huypham.instagramdemo.data.remote.response.ImageResponse;
import com.huypham.instagramdemo.data.remote.response.LoginResponse;
import com.huypham.instagramdemo.data.remote.response.MyPostListResponse;
import com.huypham.instagramdemo.data.remote.response.PostCreationResponse;
import com.huypham.instagramdemo.data.remote.response.PostDetailResponse;
import com.huypham.instagramdemo.data.remote.response.PostListResponse;
import com.huypham.instagramdemo.data.remote.response.SignUpResponse;
import com.huypham.instagramdemo.data.remote.response.UserInfoResponse;

import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

@Singleton
public interface NetworkService {

    @POST(Endpoints.LOGIN)
    Single<LoginResponse> doLoginCall(@Body LoginRequest loginRequest,
                                      @Header(Networking.HEADER_API_KEY) String apiKey);

    @POST(Endpoints.SIGNUP)
    Single<SignUpResponse> doSignUpCall(@Body SignUpRequest signUpRequest,
                                        @Header(Networking.HEADER_API_KEY) String apiKey);

    @GET(Endpoints.HOME_POST_LIST)
    Single<PostListResponse> doHomePostListCall(@Query("firstPostId") String firstPostId,
                                                @Query("lastPostId") String lastPostId,
                                                @Header(Networking.HEADER_USER_ID) String userID,
                                                @Header(Networking.HEADER_ACCESS_TOKEN) String accessToken,
                                                @Header(Networking.HEADER_API_KEY) String apiKey);

    @PUT(Endpoints.POST_LIKE)
    Single<GeneralResponse> doPostLikeCall(@Body PostLikedModifyRequest request,
                                           @Header(Networking.HEADER_USER_ID) String userID,
                                           @Header(Networking.HEADER_ACCESS_TOKEN) String accessToken,
                                           @Header(Networking.HEADER_API_KEY) String apiKey);

    @PUT(Endpoints.POST_UNLIKE)
    Single<GeneralResponse> doPostUnlikeCall(@Body PostLikedModifyRequest request,
                                             @Header(Networking.HEADER_USER_ID) String userID,
                                             @Header(Networking.HEADER_ACCESS_TOKEN) String accessToken,
                                             @Header(Networking.HEADER_API_KEY) String apiKey);

    @Multipart
    @POST(Endpoints.UPLOAD_IMAGE)
    Single<ImageResponse> doUploadImageCall(@Part MultipartBody.Part image,
                                            @Header(Networking.HEADER_USER_ID) String userID,
                                            @Header(Networking.HEADER_ACCESS_TOKEN) String accessToken,
                                            @Header(Networking.HEADER_API_KEY) String apiKey);

    @POST(Endpoints.CREATE_POST)
    Single<PostCreationResponse> doPostCreateCall(@Body PostCreateRequest request,
                                                  @Header(Networking.HEADER_USER_ID) String userID,
                                                  @Header(Networking.HEADER_ACCESS_TOKEN) String accessToken,
                                                  @Header(Networking.HEADER_API_KEY) String apiKey);

    @GET(Endpoints.USERINFO)
    Single<UserInfoResponse> doFetchUserInfoCall(@Header(Networking.HEADER_USER_ID) String userID,
                                                 @Header(Networking.HEADER_ACCESS_TOKEN) String accessToken,
                                                 @Header(Networking.HEADER_API_KEY) String apiKey);

    @DELETE(Endpoints.LOGOUT)
    Single<GeneralResponse> doLogOutCall(@Header(Networking.HEADER_USER_ID) String userID,
                                         @Header(Networking.HEADER_ACCESS_TOKEN) String accessToken,
                                         @Header(Networking.HEADER_API_KEY) String apiKey);

    @GET(Endpoints.MY_POSTS)
    Single<MyPostListResponse> doFetchMyPostsCall(@Header(Networking.HEADER_USER_ID) String userID,
                                                  @Header(Networking.HEADER_ACCESS_TOKEN) String accessToken,
                                                  @Header(Networking.HEADER_API_KEY) String apiKey);

    @PUT(Endpoints.USERINFO)
    Single<GeneralResponse> doUpdateUserInfoCall(@Body UpdateInfoRequest updateInfoBody,
                                                 @Header(Networking.HEADER_USER_ID) String userID,
                                                 @Header(Networking.HEADER_ACCESS_TOKEN) String accessToken,
                                                 @Header(Networking.HEADER_API_KEY) String apiKey);

    @GET(Endpoints.POST_DETAIL)
    Single<PostDetailResponse> doPostDetailCall(@Path("postId") String postId,
                                                @Header(Networking.HEADER_USER_ID) String userID,
                                                @Header(Networking.HEADER_ACCESS_TOKEN) String accessToken,
                                                @Header(Networking.HEADER_API_KEY) String apiKey);

}
