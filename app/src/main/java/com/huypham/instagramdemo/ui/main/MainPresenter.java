package com.huypham.instagramdemo.ui.main;

import com.huypham.instagramdemo.ui.base.BasePresenter;
import com.huypham.instagramdemo.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MainPresenter<V extends MainMvpView> extends BasePresenter<Object, V> implements MainMvpPresenter<V> {

    @Inject
    public MainPresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        onHomeSelected();
    }

    @Override
    public void onHomeSelected() {
        getMvpView().showHome();
    }

    @Override
    public void onProfileSelected() {
        getMvpView().showProfile();
    }

    @Override
    public void onPhotoSelected() {
        getMvpView().showAddPhoto();
    }

    @Override
    public void onHomeRedirection() {

    }

    @Override
    public void onLogoutRedirection() {

    }
}
