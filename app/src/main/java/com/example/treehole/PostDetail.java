package com.example.treehole;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PostDetail extends AppCompatActivity {
    private TextView postUsername;
    private TextView postTimeStamp;
    private TextView postText;
    private ListView commentListView;
    private CommentAdapter commentAdapter;
    private EditText commentUsernameInput;
    private EditText commentContentInput;
    private Post selectedPost; // Reference to the selected post

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detail);

        // Initialize the TextViews inside post_item layout
        postUsername = findViewById(R.id.username);
        postTimeStamp = findViewById(R.id.timestamp);
        postText = findViewById(R.id.postContent);

        // Get post data from intent (or retrieve post by ID if it's stored in a database)
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String timestamp = intent.getStringExtra("timestamp");
        String text = intent.getStringExtra("postText");

        // Assuming you are creating a new Post object or retrieving it by ID
        selectedPost = new Post(username, timestamp, text);

        // Set the post data to TextViews
        postUsername.setText(selectedPost.getUsername());
        postTimeStamp.setText(selectedPost.getTimestamp());
        postText.setText(selectedPost.getPostText());

        // Initialize ListView for comments and set adapter
        commentListView = findViewById(R.id.commentListView);
        commentAdapter = new CommentAdapter(this, selectedPost.getComments());
        commentListView.setAdapter(commentAdapter);

        // Initialize the input fields for username and content
        commentUsernameInput = findViewById(R.id.commentUsername);
        commentContentInput = findViewById(R.id.commentContent);
    }

    // Method to handle adding a new comment
    public void onPlusClick(View view) {
        // Get input from the EditText fields
        String username = commentUsernameInput.getText().toString().trim();
        String content = commentContentInput.getText().toString().trim();

        // Check if input fields are not empty
        if (username.isEmpty() && content.isEmpty()) {
            Toast.makeText(this, "Please enter your nickname and comment.", Toast.LENGTH_SHORT).show();
        } else if (username.isEmpty()) {
            Toast.makeText(this, "Please enter your nickname.", Toast.LENGTH_SHORT).show();
        } else if (content.isEmpty()) {
            Toast.makeText(this, "Please enter your comment.", Toast.LENGTH_SHORT).show();
        } else {
            // Get the current timestamp
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

            // Create a new Comment object and add it to the selected post
            Comment newComment = new Comment(username, timestamp, content);
            selectedPost.addComment(newComment); // Add comment to the post's list

            // Update the ListView by notifying the adapter
            commentAdapter.notifyDataSetChanged();

            // Clear the input fields
            commentUsernameInput.setText("");
            commentContentInput.setText("");
        }
    }

    public void onProfileClick(View view) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(PostDetail.this, ProfilePage.class);
            startActivity(intent);
        }, 0);
    }

    public void onNotificationClick(View view) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(PostDetail.this, NotificationScreen.class);
            startActivity(intent);
        }, 0);
    }

    public void onHomeClick(View view) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(PostDetail.this, Homepage.class);
            startActivity(intent);
        }, 0);
    }
}
