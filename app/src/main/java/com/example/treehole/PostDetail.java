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
    private List<Comment> comments; // List for comments

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detail);

        // Initialize TextViews
        postUsername = findViewById(R.id.username);
        postTimeStamp = findViewById(R.id.timestamp);
        postText = findViewById(R.id.postContent);

        // Get the post index and type from the Intent
        int postIndex = getIntent().getIntExtra("postIndex", -1);
        String type = getIntent().getStringExtra("type");
        Log.d("PostDetail", "Received postIndex: " + postIndex);

        // Check the type and retrieve the appropriate post
        if ("Academic".equals(type)) {
            if (postIndex >= 0 && postIndex < AcademicScreen.academicPostList.size()) {
                selectedPost = AcademicScreen.academicPostList.get(postIndex);
            }
        } else if ("Event".equals(type)) {
            if (postIndex >= 0 && postIndex < EventScreen.eventPostList.size()) {
                selectedPost = EventScreen.eventPostList.get(postIndex);
            }
        } else if ("Life".equals(type)) {
            if (postIndex >= 0 && postIndex < LifeScreen.lifePostList.size()) {
                selectedPost = LifeScreen.lifePostList.get(postIndex);
            }
        }

        // Handle case where post is not found
        if (selectedPost == null) {
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
        comments = selectedPost.getComments();
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

            // Create a new Comment object and add it to the selected post's list
            Comment newComment = new Comment(username, timestamp, content);
            comments.add(newComment); // Add directly to the comments list

            // Notify the adapter about data change
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

    public void onExitClick(View view) {
        finish();
    }
}
