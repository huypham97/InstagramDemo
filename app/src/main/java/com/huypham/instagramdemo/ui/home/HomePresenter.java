package com.huypham.instagramdemo.ui.home;

import android.util.Log;
import android.util.Pair;

import com.huypham.instagramdemo.data.model.Post;
import com.huypham.instagramdemo.data.model.User;
import com.huypham.instagramdemo.data.repository.PostRepository;
import com.huypham.instagramdemo.data.repository.UserRepository;
import com.huypham.instagramdemo.ui.base.BasePresenter;
import com.huypham.instagramdemo.ui.base.MvpView;
import com.huypham.instagramdemo.utils.network.NetworkUtils;
import com.huypham.instagramdemo.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.processors.PublishProcessor;

public class HomePresenter<V extends HomeMvpView> extends BasePresenter<User, V> implements HomeMvpPresenter<V> {

    private NetworkUtils networkUtils;
    private UserRepository userRepository;
    private PostRepository postRepository;
    private List<Post> allPosts;
    private PublishProcessor<Pair<String, String>> pagination;

    String firstPostId = null, lastPostId = null;

    @Inject
    public HomePresenter(SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable,
                         NetworkUtils networkUtils,
                         UserRepository userRepository,
                         PostRepository postRepository,
                         List<Post> allPosts,
                         PublishProcessor<Pair<String, String>> pagination) {
        super(schedulerProvider, compositeDisposable);
        this.networkUtils = networkUtils;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.allPosts = allPosts;
        this.pagination = pagination;
    }

    @Override
    public void onViewPrepared() {
        if (userRepository.getCurrentUser() != null)
            model = userRepository.getCurrentUser();

        compositeDisposable.add(
                pagination
                        .onBackpressureDrop()
                        .doOnNext(new Consumer<Pair<String, String>>() {
                            @Override
                            public void accept(Pair<String, String> stringStringPair) throws Throwable {
                                getMvpView().showLoading();
                            }
                        })
                        .concatMapSingle(new Function<Pair<String, String>, Single<List<Post>>>() {
                            @Override
                            public Single<List<Post>> apply(Pair<String, String> pageIds) throws Throwable {
                                return postRepository.fetchHomePostList(
                                        pageIds.first,
                                        pageIds.second,
                                        model
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
                                Log.d("TEST", "accept: ");
                                allPosts.addAll(postList);

                                firstPostId = Collections.max(allPosts).id;
                                lastPostId = Collections.min(allPosts).id;

                                getMvpView().hideLoading();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Throwable {
                                handleNetworkError(throwable);
                            }
                        })
        );
        loadMorePost();
    }

    @Override
    public void onLoadMore() {
        loadMorePost();
    }

    private void loadMorePost() {
        pagination.onNext(new Pair<>(firstPostId, lastPostId));
    }

    @Override
    public void onNewPost(Post newPost) {

    }
}
