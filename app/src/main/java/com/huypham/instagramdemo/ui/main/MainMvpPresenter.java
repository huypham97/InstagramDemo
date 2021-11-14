package com.huypham.instagramdemo.ui.main;

import com.huypham.instagramdemo.ui.base.MvpPresenter;

public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {

    void onHomeSelected();

    void onProfileSelected();

    void onPhotoSelected();

    void onHomeRedirection();

    void onLogoutRedirection();

}
