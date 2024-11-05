package com.example.treehole;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.widget.ArrayAdapter;

import com.example.treehole.Post;

import java.util.List;

public class PostAdapter extends ArrayAdapter<Post> {

    public PostAdapter(Context context, List<Post> postList) {
        super(context, 0, postList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.post_item, parent, false);
        }

        Post post = getItem(position);

        TextView username = convertView.findViewById(R.id.username);
        TextView timestamp = convertView.findViewById(R.id.timestamp);
        TextView postTitle = convertView.findViewById(R.id.postTitle);
        TextView postContent = convertView.findViewById(R.id.postContent);

        if (post != null) {
            username.setText(post.getUsername());
            timestamp.setText(post.getTimestamp());
            postTitle.setText(post.getPostTitle());
            postContent.setText(post.getPostText());

            // Change background color based on community type
            if ("Academic".equals(post.getCommunityType())) {
                convertView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.academic_round_background));
            }
            if ("Life".equals(post.getCommunityType())) {
                convertView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.life_round_background));
            }
            if ("Event".equals(post.getCommunityType())) {
                convertView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.event_round_background));
            }
        }

        return convertView;
    }
}
