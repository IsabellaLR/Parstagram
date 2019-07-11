package com.example.parstagram;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parstagram.fragments.ProfileFragment;
import com.example.parstagram.model.Post;
import com.parse.ParseFile;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return relativeDate;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Post post = posts.get(position);
        holder.bind(post);

        holder.pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // handle click here
                Intent i = new Intent(context, DetailsActivity.class);
                i.putExtra("date", getRelativeTimeAgo(String.valueOf(post.getCreatedAt())));
                i.putExtra("image", post.getImage().getUrl());
                i.putExtra("user", post.getUser().getUsername());
                i.putExtra("description", post.getDescription());
                i.putExtra("profile", post.getProfile());
                ((Activity) context).startActivityForResult(i, 20);
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView username;
        private ImageView pic;
        private TextView description;
        private TextView date;
        private ImageView profile;
        FragmentManager fragmentManager;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            pic = itemView.findViewById(R.id.pic);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
            profile = itemView.findViewById(R.id.profile);
            fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        }

        public void bind(Post post){
            username.setText(post.getUser().getUsername());
            date.setText(getRelativeTimeAgo(String.valueOf(post.getCreatedAt())));
            ParseFile image = post.getImage();
            if (image != null){
                Glide.with(context).load(image.getUrl()).into(pic);
            }
            description.setText(post.getDescription());
            ParseFile image2 = post.getProfile();
            if (image2 != null){
                Glide.with(context).load(image2.getUrl()).into(profile);
            }

            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    fragmentManager.beginTransaction().replace(R.id.f1Container, new ProfileFragment()).commit();
                }
            });
        }
    }
}
