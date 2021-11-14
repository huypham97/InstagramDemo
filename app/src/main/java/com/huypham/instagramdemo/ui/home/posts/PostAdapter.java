package com.huypham.instagramdemo.ui.home.posts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huypham.instagramdemo.R;
import com.huypham.instagramdemo.data.model.Post;
import com.huypham.instagramdemo.ui.base.BaseViewHolder;
import com.huypham.instagramdemo.utils.common.TimeUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Post> posts;

    private Context context;

    private RecyclerView recyclerView;

    public PostAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull @NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull @NotNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    @NonNull
    @NotNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_post, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void addItem(List<Post> postList) {
        posts.clear();
        posts.addAll(postList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends BaseViewHolder {

        private TextView tvName, tvTime, tvLikesCount;
        private ImageView ivProfile, ivLike, ivPost;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvLikesCount = itemView.findViewById(R.id.tvLikesCount);
            ivProfile = itemView.findViewById(R.id.ivProfile);
            ivLike = itemView.findViewById(R.id.ivLike);
            ivPost = itemView.findViewById(R.id.ivPost);
        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            Post post = posts.get(position);

            tvName.setText(post.creator.name);
            tvTime.setText(TimeUtils.getTimeAgo(post.createdAt));
            tvLikesCount.setText(context.getResources().getString(R.string.post_like_label, post.likedBy.size()));
            for (Post.User postUser : post.likedBy) {
                if (postUser.id == us)
            }
        }
    }
}
