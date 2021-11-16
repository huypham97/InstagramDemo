package com.huypham.instagramdemo.ui.home;

import com.huypham.instagramdemo.data.model.Post;
import com.huypham.instagramdemo.ui.base.MvpView;

import java.util.List;

public interface HomeMvpView extends MvpView {

    void updatePost(List<Post> postList);

}
