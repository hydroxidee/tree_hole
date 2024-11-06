package com.example.treehole;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.ArrayAdapter;
import java.util.List;

public class CommentAdapter extends ArrayAdapter<Comment> {

    public CommentAdapter(Context context, List<Comment> commentList) {
        super(context, 0, commentList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment_item, parent, false);
        }

        // Get the comment for this position
        Comment comment = getItem(position);

        // Find the TextViews for comment data
        TextView username = convertView.findViewById(R.id.commentUsername);
        TextView timestamp = convertView.findViewById(R.id.commentTimestamp);
        TextView commentText = convertView.findViewById(R.id.commentText);
//hello
        // Populate the data into the TextViews
        assert comment != null;
        username.setText(comment.getUsername());
        timestamp.setText(comment.getTimestamp());
        commentText.setText(comment.getCommentText());
    // hello
        return convertView;
    }
}
