package com.huypham.instagramdemo.ui.base;

import android.util.Log;

import com.huypham.instagramdemo.R;
import com.huypham.instagramdemo.utils.network.NetworkError;
import com.huypham.instagramdemo.utils.network.NetworkUtils;
import com.huypham.instagramdemo.utils.rx.SchedulerProvider;

import java.net.HttpURLConnection;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    protected SchedulerProvider schedulerProvider;
    protected CompositeDisposable compositeDisposable;

    private V mvpView;

    @Inject
    public BasePresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        this.schedulerProvider = schedulerProvider;
        this.compositeDisposable = compositeDisposable;
    }

    public SchedulerProvider getSchedulerProvider() {
        return schedulerProvider;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public V getMvpView() {
        return mvpView;
    }

    @Override
    public void onAttach(V mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void onDetach() {
        compositeDisposable.dispose();
        mvpView = null;
    }

    @Override
    public void handleNetworkError(Throwable err) {
        NetworkError networkError = NetworkUtils.castToNetworkError(err);
        switch (networkError.getStatus()) {
            case -1:
                getMvpView().showMessage(R.string.network_default_error);
                break;
            case 0:
                getMvpView().showMessage(R.string.server_connection_error);
                break;
            case HttpsURLConnection.HTTP_UNAUTHORIZED:
                forcedLogoutUser();
                getMvpView().showMessage(R.string.server_connection_error);
                break;
            case HttpsURLConnection.HTTP_INTERNAL_ERROR:
                getMvpView().showMessage(R.string.network_internal_error);
                break;
            case HttpURLConnection.HTTP_UNAVAILABLE:
                getMvpView().showMessage(R.string.network_server_not_available);
                break;
            default:
                getMvpView().showMessage(networkError.getMessage());
                break;
        }
    }

    protected void forcedLogoutUser() {

    }

    public boolean isViewAttached() {
        return mvpView != null;
    }

}
