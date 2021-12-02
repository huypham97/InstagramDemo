package com.huypham.instagramdemo.ui.home.posts;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.huypham.instagramdemo.R;
import com.huypham.instagramdemo.data.model.Image;
import com.huypham.instagramdemo.data.model.Post;
import com.huypham.instagramdemo.di.component.ViewHolderComponent;
import com.huypham.instagramdemo.ui.base.BaseItemViewHolder;
import com.huypham.instagramdemo.utils.common.GlideHelper;

import androidx.lifecycle.Observer;

public class PostItemViewHolder extends BaseItemViewHolder<Post, PostItemViewModel> {

    private TextView tvName, tvLikesCount, tvTime;
    private ImageView ivProfile, ivPost, ivLike;

    public PostItemViewHolder(ViewGroup parent) {
        super(R.layout.layout_item_post, parent);
    }

    @Override
    protected void injectDependencies(ViewHolderComponent buildViewHolderComponent) {
        buildViewHolderComponent.inject(this);
    }

    @Override
    protected void setupObserver() {
        super.setupObserver();

        viewModel.name.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String name) {
                tvName.setText(name);
            }
        });

        viewModel.postTime.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String time) {
                tvTime.setText(time);
            }
        });

        viewModel.likesCount.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer likesCount) {
                tvLikesCount.setText(itemView.getContext().getString(R.string.post_like_label, likesCount));
            }
        });

        viewModel.isLiked.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLiked) {
                if (isLiked) {
                    ivLike.setImageResource(R.drawable.ic_heart_selected);
                } else {
                    ivLike.setImageResource(R.drawable.ic_heart_unselected);
                }
            }
        });

        viewModel.profileImage.observe(this, new Observer<Image>() {
            @Override
            public void onChanged(Image image) {
                if (image.url != null) {
                    RequestBuilder<Drawable> glideRequest = Glide
                            .with(ivProfile.getContext())
                            .load(GlideHelper.getProtectedUrl(image.url, image.headers))
                            .apply(RequestOptions.centerCropTransform())
                            .apply(RequestOptions.placeholderOf(R.drawable.ic_profile_selected));

                    if (image.placeholderWidth > 0 && image.placeholderHeight > 0) {
                        ViewGroup.LayoutParams params = ivProfile.getLayoutParams();
                        params.width = image.placeholderWidth;
                        params.height = image.placeholderHeight;
                        ivProfile.setLayoutParams(params);
                        glideRequest
                                .apply(RequestOptions.overrideOf(image.placeholderWidth, image.placeholderHeight))
                                .apply(RequestOptions.placeholderOf(R.drawable.ic_profile_unselected));
                    }
                    glideRequest.into(ivProfile);
                } else {
                    ivProfile.setImageResource(R.drawable.ic_profile);
                }
            }
        });

        viewModel.postImageDetail.observe(this, new Observer<Image>() {
            @Override
            public void onChanged(Image image) {
                RequestBuilder<Drawable> glideRequest = Glide
                        .with(ivPost.getContext())
                        .load(GlideHelper.getProtectedUrl(image.url, image.headers));

                if (image.placeholderWidth > 0 && image.placeholderHeight > 0) {
                    ViewGroup.LayoutParams params = ivPost.getLayoutParams();
                    params.width = image.placeholderWidth;
                    params.height = image.placeholderHeight;
                    ivPost.setLayoutParams(params);
                    glideRequest
                            .apply(RequestOptions.overrideOf(image.placeholderWidth, image.placeholderHeight))
                            .apply(RequestOptions.placeholderOf(R.drawable.ic_photo));
                }
                glideRequest.into(ivPost);
            }
        });
    }

    @Override
    protected void setupView(View view) {
        tvName = view.findViewById(R.id.tvName);
        tvLikesCount = view.findViewById(R.id.tvLikesCount);
        tvTime = view.findViewById(R.id.tvTime);
        ivProfile = view.findViewById(R.id.ivProfile);
        ivPost = view.findViewById(R.id.ivPost);
        ivLike = view.findViewById(R.id.ivLike);

        ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.onLikeClick();
            }
        });
    }
}
