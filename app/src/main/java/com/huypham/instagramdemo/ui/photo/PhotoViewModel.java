package com.huypham.instagramdemo.ui.photo;

import android.util.Pair;

import com.huypham.instagramdemo.R;
import com.huypham.instagramdemo.data.model.Post;
import com.huypham.instagramdemo.data.model.User;
import com.huypham.instagramdemo.data.repository.PhotoRepository;
import com.huypham.instagramdemo.data.repository.PostRepository;
import com.huypham.instagramdemo.data.repository.UserRepository;
import com.huypham.instagramdemo.ui.base.BaseViewModel;
import com.huypham.instagramdemo.utils.common.FileHelper;
import com.huypham.instagramdemo.utils.network.NetworkUtils;
import com.huypham.instagramdemo.utils.rx.SchedulerProvider;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.Callable;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;

public class PhotoViewModel extends BaseViewModel {

    private UserRepository userRepository;
    private PostRepository postRepository;
    private PhotoRepository photoRepository;
    private File directory;
    private FileHelper fileHelper;

    public PhotoViewModel(SchedulerProvider schedulerProvider,
                          CompositeDisposable compositeDisposable,
                          NetworkUtils networkUtils,
                          UserRepository userRepository,
                          PostRepository postRepository,
                          PhotoRepository photoRepository,
                          File directory,
                          FileHelper fileHelper) {
        super(schedulerProvider, compositeDisposable, networkUtils);
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.photoRepository = photoRepository;
        this.directory = directory;
        this.fileHelper = fileHelper;

        user = userRepository.getCurrentUser();
    }

    private User user;

    MutableLiveData<Boolean> loading = new MutableLiveData<>();

    MutableLiveData<Post> post = new MutableLiveData<>();

    @Override
    protected void onCreate() {

    }

    public void onGalleryImageSelected(InputStream inputStream) {
        loading.postValue(true);
        compositeDisposable.add(Single.fromCallable(new Callable<File>() {
                                                        @Override
                                                        public File call() throws Exception {
                                                            return fileHelper.saveInputStreamToFile(
                                                                    inputStream,
                                                                    directory,
                                                                    "gallery_img_temp",
                                                                    500);
                                                        }
                                                    }
                )
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(new Consumer<File>() {
                            @Override
                            public void accept(File file) throws Throwable {
                                if (file != null) {
                                    loading.postValue(false);
                                    uploadPhotoAndCreatePost(file, fileHelper.getImageSize(file));
                                } else {
                                    loading.postValue(false);
                                    messageStringId.postValue(R.string.try_again);
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Throwable {
                                loading.postValue(false);
                                messageStringId.postValue(R.string.try_again);
                            }
                        })
        );
    }

    public void onCameraImageTaken(String cameraImageProcessor) {
        loading.postValue(true);
        compositeDisposable.add(Single.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return cameraImageProcessor;
            }
        })
                .subscribeOn(schedulerProvider.io())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String path) throws Throwable {
                        File file = fileHelper.makeFile(path);
                        loading.postValue(false);
                        uploadPhotoAndCreatePost(file, fileHelper.getImageSize(file));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        loading.postValue(false);
                        messageStringId.postValue(R.string.try_again);
                    }
                }));
    }

    private void uploadPhotoAndCreatePost(File imageFile, Pair<Integer, Integer> imageSize) {
        loading.postValue(true);
        compositeDisposable.add(
                photoRepository.uploadFile(imageFile, user)
                        // Upload file simultaneously Create a new post
                        .flatMap(new Function<String, Single<Post>>() {
                            @Override
                            public Single<Post> apply(String imageUrl) throws Throwable {
                                return postRepository.createPost(imageUrl, imageSize.first, imageSize.second, user);
                            }
                        })
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(new Consumer<Post>() {
                            @Override
                            public void accept(Post postIt) throws Throwable {
                                loading.postValue(false);
                                post.postValue(postIt);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Throwable {
                                loading.postValue(false);
                                handleNetworkError(throwable);
                            }
                        })
        );
    }
}
