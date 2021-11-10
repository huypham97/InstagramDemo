package com.huypham.instagramdemo.ui.base;

public interface MvpPresenter<V extends MvpView> {
    void onAttach(V mvpView);

    void onDetach();

    void handleNetworkError(Throwable err);
}
