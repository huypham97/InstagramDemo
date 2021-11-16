package com.huypham.instagramdemo.ui.home.posts;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.huypham.instagramdemo.R;
import com.huypham.instagramdemo.data.model.Image;
import com.huypham.instagramdemo.data.model.Post;
import com.huypham.instagramdemo.data.remote.Networking;
import com.huypham.instagramdemo.data.repository.UserRepository;
import com.huypham.instagramdemo.ui.base.BaseViewHolder;
import com.huypham.instagramdemo.utils.common.GlideHelper;
import com.huypham.instagramdemo.utils.common.TimeUtils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Post> postList;
    private UserRepository userRepository;

    public PostAdapter(List<Post> postList, UserRepository userRepository) {
        this.postList = postList;
        this.userRepository = userRepository;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_post, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public void addItems(List<Post> postList) {
        this.postList.addAll(postList);
        notifyDataSetChanged();
    }

    public class PostViewHolder extends BaseViewHolder {

        TextView tvName, tvLikesCount, tvTime;
        ImageView ivLike, ivProfile, ivPost;

        Map<String, String> headers = new HashMap<>();

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvLikesCount = itemView.findViewById(R.id.tvLikesCount);
            tvTime = itemView.findViewById(R.id.tvTime);
            ivLike = itemView.findViewById(R.id.ivLike);
            ivProfile = itemView.findViewById(R.id.ivProfile);
            ivPost = itemView.findViewById(R.id.ivPost);

            headers.put(Networking.HEADER_API_KEY, Networking.API_KEY);
            headers.put(Networking.HEADER_USER_ID, userRepository.getCurrentUser().getId());
            headers.put(Networking.HEADER_ACCESS_TOKEN, userRepository.getCurrentUser().getAccessToken());
        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            final Post post = postList.get(position);

            tvName.setText(post.creator.name);

            tvTime.setText(TimeUtils.getTimeAgo(post.createdAt));

            tvLikesCount.setText(itemView.getContext().getString(R.string.post_like_label, post.likedBy.size()));

            if (isLiked(post)) {
                ivLike.setImageResource(R.drawable.ic_heart_selected);
            } else {
                ivLike.setImageResource(R.drawable.ic_heart_unselected);
            }

            Image imgPost = new Image(post.imgUrl, headers);
            RequestBuilder<Drawable> glideRequest = Glide.with(ivProfile.getContext())
                    .load(GlideHelper.getProtectedUrl())
        }

        private boolean isLiked(Post post) {
            for (Post.User postUser : post.likedBy) {
                if (postUser.id == userRepository.getCurrentUser().getId())
                    return true;
            }
            return false;
        }
    }
}
