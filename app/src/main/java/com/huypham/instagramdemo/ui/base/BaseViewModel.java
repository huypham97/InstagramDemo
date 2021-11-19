package com.huypham.instagramdemo.ui.base;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.huypham.instagramdemo.R;
import com.huypham.instagramdemo.utils.network.NetworkError;
import com.huypham.instagramdemo.utils.network.NetworkUtils;
import com.huypham.instagramdemo.utils.rx.SchedulerProvider;

import java.net.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public abstract class BaseViewModel extends ViewModel {

    protected SchedulerProvider schedulerProvider;
    protected CompositeDisposable compositeDisposable;
    protected NetworkUtils networkUtils;

    protected MutableLiveData<Integer> messageStringId = new MutableLiveData<>();
    protected MutableLiveData<String> messageString = new MutableLiveData<>();

    public BaseViewModel(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, NetworkUtils networkUtils) {
        this.schedulerProvider = schedulerProvider;
        this.compositeDisposable = compositeDisposable;
        this.networkUtils = networkUtils;
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }

    protected boolean checkInternetConnectionWithMessage() {
        if (NetworkUtils.isNetworkConnected()) {
            return true;
        } else {
            messageStringId.postValue(R.string.network_connection_error);
            return false;
        }
    }

    protected boolean checkInternetConnection() {
        return NetworkUtils.isNetworkConnected();
    }

    protected void handleNetworkError(Throwable err) {
        NetworkError networkError = NetworkUtils.castToNetworkError(err);
        switch (networkError.getStatus()) {
            case -1:
                messageStringId.postValue(R.string.network_default_error);
                break;
            case 0:
                messageStringId.postValue(R.string.server_connection_error);
                break;
            case HttpsURLConnection.HTTP_UNAUTHORIZED:
                messageStringId.postValue(R.string.server_connection_error);
                break;
            case HttpsURLConnection.HTTP_INTERNAL_ERROR:
                messageStringId.postValue(R.string.network_internal_error);
                break;
            case HttpURLConnection.HTTP_UNAVAILABLE:
                messageStringId.postValue(R.string.network_server_not_available);
                break;
            default:
                messageString.postValue(networkError.getMessage());
                break;
        }
    }

    protected abstract void onCreate();
}
