package com.huypham.instagramdemo.ui.photo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.huypham.instagramdemo.R;
import com.huypham.instagramdemo.data.model.Post;
import com.huypham.instagramdemo.di.component.FragmentComponent;
import com.huypham.instagramdemo.ui.base.BaseFragment;
import com.huypham.instagramdemo.ui.main.MainSharedViewModel;
import com.huypham.instagramdemo.ui.profile.ProfileFragment;
import com.mindorks.paracamera.Camera;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhotoFragment extends BaseFragment<PhotoViewModel> {

    public static final String TAG = "PhotoFragment";

    public static final int RESULT_IMG_GALLERY = 1000;

    private ProgressBar pbLoading;
    private View viewGallery, viewCamera;

    private PhotoFragment() {
        // Required empty public constructor
    }

    public static PhotoFragment newInstance() {
        Bundle args = new Bundle();
        PhotoFragment fragment = new PhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    Camera camera;

    @Inject
    MainSharedViewModel mainSharedViewModel;

    @Override
    protected int provideLayoutId() {
        return R.layout.fragment_photo;
    }

    @Override
    protected void injectDependencies(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected void setupObserver() {
        super.setupObserver();

        viewModel.loading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading)
                    pbLoading.setVisibility(View.VISIBLE);
                else
                    pbLoading.setVisibility(View.GONE);
            }
        });

        viewModel.post.observe(this, new Observer<Post>() {
            @Override
            public void onChanged(Post post) {
                mainSharedViewModel.newPost.postValue(post);
                mainSharedViewModel.onHomeRedirection();
            }
        });
    }

    @Override
    protected void setupView(View view) {
        pbLoading = view.findViewById(R.id.pbLoading);
        viewCamera = view.findViewById(R.id.viewCamera);
        viewGallery = view.findViewById(R.id.viewGallery);

        viewGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, RESULT_IMG_GALLERY);
            }
        });

        viewCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    camera.takePicture();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
        }
    }
}