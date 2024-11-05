package com.example.treehole;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
    private HashMap<String,Object> commentHash;
    private List<Comment> comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detail);

        // Initialize TextViews
        postUsername = findViewById(R.id.username);
        postTimeStamp = findViewById(R.id.timestamp);
        postText = findViewById(R.id.postContent);

        // Get the post index from the Intent
        int postIndex = getIntent().getIntExtra("postIndex", -1);
        Log.d("PostDetail", "Received postIndex: " + postIndex);

        // Retrieve the selected Post from AcademicScreen's postList
        if (postIndex >= 0 && postIndex < AcademicScreen.postList.size()) {
            selectedPost = AcademicScreen.postList.get(postIndex);
        } else {
            Toast.makeText(this, "Error loading post", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Display the post data in TextViews
        postUsername.setText(selectedPost.getUsername());
        postTimeStamp.setText(selectedPost.getTimestamp());
        postText.setText(selectedPost.getPostText());

        // Initialize ListView for comments and set adapter
        commentListView = findViewById(R.id.commentListView);
        comments = selectedPost.getComments() != null ? selectedPost.getComments() : new ArrayList<>();
        commentAdapter = new CommentAdapter(this, comments);
        commentListView.setAdapter(commentAdapter);

        // Initialize input fields for adding comments
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
    public void onExitClick(View view){
        finish();
    }
}
