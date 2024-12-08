package com.example.treehole;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.ArrayAdapter;
import java.util.List;
import android.widget.Button;
import android.widget.Toast;


public class CommentAdapter extends ArrayAdapter<Comment> {
    Spinner names;
    EditText content;

    public CommentAdapter(Context context, List<Comment> commentList) {
        super(context, 0, commentList);
    }

    @SuppressLint("SetTextI18n")
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

        Button replyToReplyButton = convertView.findViewById(R.id.replyToReplyButton);
        content = convertView.findViewById(R.id.commentCommentContent);

        View finalConvertView = convertView;
        replyToReplyButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "plus button clicked");

                String username = names.getSelectedItem().toString().trim();
                String reply = content.getText().toString().trim();

                if (username.isEmpty() || "Option".equals(username) || reply.isEmpty()) {
                    return;
                }

                TextView commentText = finalConvertView.findViewById(R.id.commentText);

                String newText = commentText.getText().toString();
                newText = newText + "\n" + username + " :" + reply;

                commentText.setText(newText);
            }
        });

        names = convertView.findViewById(R.id.replyToReplyUser);
        String name = UserInfo.GetUser();
        String[] nameOptions = {"Option",name, "Anonymous"};
        ArrayAdapter<String> nameAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, nameOptions);
        nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        names.setAdapter(nameAdapter);

        return convertView;
    }

}