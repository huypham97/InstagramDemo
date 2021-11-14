package com.huypham.instagramdemo.ui.main;

import com.huypham.instagramdemo.ui.base.MvpView;

public interface MainMvpView extends MvpView {

    void showHome();

    void showProfile();

    void showAddPhoto();

    void homeRedirection();

    void logOutRedirection();

}
