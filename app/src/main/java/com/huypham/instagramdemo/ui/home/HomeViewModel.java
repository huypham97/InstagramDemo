package com.huypham.instagramdemo.ui.home;

import android.util.Log;
import android.util.Pair;

import com.huypham.instagramdemo.data.model.Post;
import com.huypham.instagramdemo.data.model.User;
import com.huypham.instagramdemo.data.repository.PostRepository;
import com.huypham.instagramdemo.data.repository.UserRepository;
import com.huypham.instagramdemo.ui.base.BaseViewModel;
import com.huypham.instagramdemo.utils.network.NetworkUtils;
import com.huypham.instagramdemo.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.processors.PublishProcessor;

public class HomeViewModel extends BaseViewModel {

    private UserRepository userRepository;
    private PostRepository postRepository;
    private ArrayList<Post> allPosts;
    private PublishProcessor<Pair<String, String>> pagination;

    public HomeViewModel(SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable,
                         NetworkUtils networkUtils,
                         UserRepository userRepository,
                         PostRepository postRepository,
                         ArrayList<Post> allPosts,
                         PublishProcessor<Pair<String, String>> pagination) {
        super(schedulerProvider, compositeDisposable, networkUtils);
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.allPosts = allPosts;
        this.pagination = pagination;

        user = userRepository.getCurrentUser();

        init();
    }

    MutableLiveData<Boolean> loading = new MutableLiveData<>();

    MutableLiveData<List<Post>> posts = new MutableLiveData<>();

    private User user;

    MutableLiveData<List<Post>> refreshPostList = new MutableLiveData<>();

    String firstPostId = null;

    String lastPostId = null;

    private void init() {
        compositeDisposable.add(
                pagination
                        .onBackpressureDrop()
                        .doOnNext(new Consumer<Pair<String, String>>() {
                            @Override
                            public void accept(Pair<String, String> stringStringPair) throws Throwable {
                                loading.postValue(true);
                            }
                        })
                        .concatMapSingle(new Function<Pair<String, String>, Single<List<Post>>>() {
                            @Override
                            public Single<List<Post>> apply(Pair<String, String> pageIds) throws Throwable {
                                return postRepository.fetchHomePostList(
                                        pageIds.first,
                                        pageIds.second,
                                        user
                                )
                                        .subscribeOn(schedulerProvider.io())
                                        .observeOn(schedulerProvider.ui())
                                        .doOnError(new Consumer<Throwable>() {
                                            @Override
                                            public void accept(Throwable throwable) throws Throwable {
                                                handleNetworkError(throwable);
                                            }
                                        });
                            }
                        })
                        .subscribe(new Consumer<List<Post>>() {
                            @Override
                            public void accept(List<Post> postList) throws Throwable {
                                allPosts.addAll(postList);

                                firstPostId = Collections.max(allPosts).id;
                                lastPostId = Collections.min(allPosts).id;

                                loading.postValue(false);
                                posts.postValue(postList);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Throwable {
                                handleNetworkError(throwable);
                            }
                        })
        );
    }

    @Override
    protected void onCreate() {
        loadMorePosts();
    }

    private void loadMorePosts() {
        if (checkInternetConnectionWithMessage()) {
            pagination.onNext(new Pair<>(firstPostId, lastPostId));
        }
    }

    public void onLoadMore() {
        if (loading.getValue() != null && loading.getValue() == false)
            loadMorePosts();
    }

    public void onNewPost(Post newPost) {
        allPosts.add(0, newPost);
        refreshPostList.postValue(allPosts);
    }
}
