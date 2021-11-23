package com.huypham.instagramdemo.ui.home.posts;

import android.util.Log;

import com.huypham.instagramdemo.R;
import com.huypham.instagramdemo.data.model.Image;
import com.huypham.instagramdemo.data.model.Post;
import com.huypham.instagramdemo.data.model.User;
import com.huypham.instagramdemo.data.remote.Networking;
import com.huypham.instagramdemo.data.repository.PostRepository;
import com.huypham.instagramdemo.data.repository.UserRepository;
import com.huypham.instagramdemo.ui.base.BaseItemViewModel;
import com.huypham.instagramdemo.utils.common.TimeUtils;
import com.huypham.instagramdemo.utils.display.ScreenResourceProvider;
import com.huypham.instagramdemo.utils.network.NetworkUtils;
import com.huypham.instagramdemo.utils.rx.SchedulerProvider;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;

public class PostItemViewModel extends BaseItemViewModel<Post> {

    public static final String TAG = "PostItemViewModel";

    private UserRepository userRepository;
    private PostRepository postRepository;
    private ScreenResourceProvider screenResourceProvider;

    private User user;
    private int screenWidth;
    private int screenHeight;
    private Map<String, String> headers;

    LiveData<String> name = Transformations.map(data, new Function<Post, String>() {
        @Override
        public String apply(Post input) {
            return input.creator.name;
        }
    });

    LiveData<String> postTime = Transformations.map(data, new Function<Post, String>() {
        @Override
        public String apply(Post input) {
            return TimeUtils.getTimeAgo(input.createdAt);
        }
    });

    LiveData<Integer> likesCount = Transformations.map(data, new Function<Post, Integer>() {
        @Override
        public Integer apply(Post input) {
            return input.likedBy.size();
        }
    });

    LiveData<Boolean> isLiked = Transformations.map(data, new Function<Post, Boolean>() {
        @Override
        public Boolean apply(Post input) {
            for (Post.User postUser : input.likedBy) {
                if (postUser.id.equals(user.getId())) {
                    return true;
                }
            }
            return false;
        }
    });

    LiveData<Image> profileImage = Transformations.map(data, new Function<Post, Image>() {
        @Override
        public Image apply(Post input) {
            Log.d("TEST", "apply: " + input.creator.profilePicUrl);
            return new Image(input.creator.profilePicUrl, headers);
        }
    });

    LiveData<Image> postImageDetail = Transformations.map(data, new Function<Post, Image>() {
        @Override
        public Image apply(Post input) {
            return new Image(
                    input.imgUrl,
                    headers,
                    screenWidth,
                    (input.imgHeight != 0) ? (int) (calculateScaleFactor(input) * input.imgHeight) : screenHeight / 3
            );
        }
    });

    private float calculateScaleFactor(Post post) {
        return (post.imgWidth != 0) ? (float) (screenWidth / post.imgWidth) : 1f;
    }

    @Inject
    public PostItemViewModel(SchedulerProvider schedulerProvider,
                             CompositeDisposable compositeDisposable,
                             NetworkUtils networkUtils,
                             UserRepository userRepository,
                             PostRepository postRepository,
                             ScreenResourceProvider screenResourceProvider) {
        super(schedulerProvider, compositeDisposable, networkUtils);
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.screenResourceProvider = screenResourceProvider;

        init();
    }

    private void init() {
        user = userRepository.getCurrentUser();

        screenWidth = screenResourceProvider.getScreenWidth();
        screenHeight = screenResourceProvider.getScreenHeight();

        headers = new HashMap<String, String>() {
            {
                put(Networking.HEADER_API_KEY, Networking.API_KEY);
                put(Networking.HEADER_USER_ID, user.getId());
                put(Networking.HEADER_ACCESS_TOKEN, user.getAccessToken());
            }
        };
    }

    @Override
    protected void onCreate() {
        Log.d(TAG, "onCreate:");
    }

    public void onLikeClick() {
        Post post = data.getValue();
        Single<Post> api;

        if (networkUtils.isNetworkConnected()) {
            if (isLiked.getValue() == true) {
                api = postRepository.makeUnlikePost(post, user);
            } else {
                api = postRepository.makeLikePost(post, user);
            }
            compositeDisposable.add(
                    api.subscribeOn(schedulerProvider.io())
                    .subscribe(new Consumer<Post>() {
                        @Override
                        public void accept(Post responsePost) throws Throwable {
                            if (responsePost.id == post.id)
                                updateData(responsePost);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Throwable {
                            handleNetworkError(throwable);
                        }
                    })
            );
        } else {
            messageStringId.postValue(R.string.network_connection_error);
        }
    }
}
