package com.huypham.instagramdemo.data.repository;

import com.huypham.instagramdemo.data.model.Post;
import com.huypham.instagramdemo.data.model.User;
import com.huypham.instagramdemo.data.remote.NetworkService;
import com.huypham.instagramdemo.data.remote.Networking;
import com.huypham.instagramdemo.data.remote.request.PostCreateRequest;
import com.huypham.instagramdemo.data.remote.request.PostLikedModifyRequest;
import com.huypham.instagramdemo.data.remote.response.GeneralResponse;
import com.huypham.instagramdemo.data.remote.response.MyPostListResponse;
import com.huypham.instagramdemo.data.remote.response.PostCreationResponse;
import com.huypham.instagramdemo.data.remote.response.PostDetailResponse;
import com.huypham.instagramdemo.data.remote.response.PostListResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Function;

public class PostRepository {

    private NetworkService networkService;

    @Inject
    public PostRepository(NetworkService networkService) {
        this.networkService = networkService;
    }

    public Single<List<Post>> fetchHomePostList(String firstPostId, String lastPostId, User user) {
        return networkService.doHomePostListCall(
                firstPostId,
                lastPostId,
                user.getId(),
                user.getAccessToken(),
                Networking.API_KEY
        ).map(new Function<PostListResponse, List<Post>>() {
            @Override
            public List<Post> apply(PostListResponse postListResponse) throws Throwable {
                return postListResponse.data;
            }
        });
    }

    public Single<Post> makeLikePost(Post post, User user) {
        return networkService.doPostLikeCall(
                new PostLikedModifyRequest(post.id),
                user.getId(),
                user.getAccessToken(),
                Networking.API_KEY
        ).map(new Function<GeneralResponse, Post>() {
            @Override
            public Post apply(GeneralResponse generalResponse) throws Throwable {
                boolean checkUserLiked = false;
                for (Post.User userLikedBy : post.likedBy) {
                    if (userLikedBy.id.equals(user.getId())) {
                        checkUserLiked = true;
                    }
                }

                if (!checkUserLiked) {
                    post.likedBy.add(new Post.User(
                                    user.getId(),
                                    user.getName(),
                                    user.getProfilePicUrl()
                            )
                    );
                }

                return post;
            }
        });
    }

    public Single<Post> makeUnlikePost(Post post, User user) {
        return networkService.doPostUnlikeCall(
                new PostLikedModifyRequest(post.id),
                user.getId(),
                user.getAccessToken(),
                Networking.API_KEY
        ).map(new Function<GeneralResponse, Post>() {
            @Override
            public Post apply(GeneralResponse generalResponse) throws Throwable {
                for (Post.User userLikedBy : post.likedBy) {
                    if (userLikedBy.id.equals(user.getId())) {
                        post.likedBy.remove(userLikedBy);
                        break;
                    }
                }
                return post;
            }
        });
    }

    public Single<Post> createPost(String imgUrl, int imgWidth, int imgHeight, User user) {
        return networkService.doPostCreateCall(
                new PostCreateRequest(imgUrl, imgWidth, imgHeight),
                user.getId(),
                user.getAccessToken(),
                Networking.API_KEY
        ).map(new Function<PostCreationResponse, Post>() {
            @Override
            public Post apply(PostCreationResponse postCreationResponse) throws Throwable {
                return new Post(
                        postCreationResponse.data.id,
                        postCreationResponse.data.imgUrl,
                        postCreationResponse.data.imgWidth,
                        postCreationResponse.data.imgHeight,
                        new Post.User(
                                user.getId(),
                                user.getName(),
                                user.getProfilePicUrl()
                        ),
                        new ArrayList<Post.User>(),
                        postCreationResponse.data.createdAt
                );
            }
        });
    }

    public Single<List<MyPostListResponse.MyPost>> fetchMyPostList(User user) {
        return networkService.doFetchMyPostsCall(user.getId(), user.getAccessToken(), Networking.API_KEY)
                .map(new Function<MyPostListResponse, List<MyPostListResponse.MyPost>>() {
                    @Override
                    public List<MyPostListResponse.MyPost> apply(MyPostListResponse myPostListResponse) throws Throwable {
                        return myPostListResponse.data;
                    }
                });
    }

    public Single<Post> fetchPostDetail(String postId, User user) {
        return networkService.doPostDetailCall(postId, user.getId(), user.getAccessToken(), Networking.API_KEY)
                .map(new Function<PostDetailResponse, Post>() {
                    @Override
                    public Post apply(PostDetailResponse postDetailResponse) throws Throwable {
                        return postDetailResponse.data;
                    }
                });
    }

}
