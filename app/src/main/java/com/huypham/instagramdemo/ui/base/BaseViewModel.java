package com.huypham.instagramdemo.ui.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cmc.androidtraining.R;
import com.cmc.androidtraining.utils.NetworkError;
import com.cmc.androidtraining.utils.NetworkHelper;

import java.net.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;

public abstract class BaseViewModel extends ViewModel {

    MutableLiveData<Integer> messageStringId = new MutableLiveData<>();
    MutableLiveData<String> messageString = new MutableLiveData<>();

    protected boolean checkInternetConnectionWithMessage() {
        if (NetworkHelper.isNetworkConnected()) {
            return true;
        } else {
            messageStringId.postValue(R.string.network_connection_error);
            return false;
        }
    }

    protected boolean checkInternetConnection() {
        return NetworkHelper.isNetworkConnected();
    }

    protected void handleNetworkError(Throwable err) {
        NetworkError networkError = NetworkHelper.castToNetworkError(err);
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
