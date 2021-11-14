package com.huypham.instagramdemo.ui.home;

import com.huypham.instagramdemo.data.model.Post;
import com.huypham.instagramdemo.ui.base.MvpPresenter;

public interface HomeMvpPresenter<V extends HomeMvpView> extends MvpPresenter<V> {

    void onViewPrepared();

    void onLoadMore();

    void onNewPost(Post newPost);

}
