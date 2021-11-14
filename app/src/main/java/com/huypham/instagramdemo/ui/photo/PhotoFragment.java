package com.huypham.instagramdemo.ui.photo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huypham.instagramdemo.R;
import com.huypham.instagramdemo.ui.profile.ProfileFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhotoFragment extends Fragment {

    public static final String TAG = "PhotoFragment";

    private PhotoFragment() {
        // Required empty public constructor
    }

    public static PhotoFragment newInstance() {
        Bundle args = new Bundle();
        PhotoFragment fragment = new PhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo, container, false);
    }
}