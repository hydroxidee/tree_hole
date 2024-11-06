package com.example.treehole;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class PostDetail extends AppCompatActivity {
    private TextView postUsername, postTimeStamp, postText, postTitle;
    private ListView commentListView;
    private EditText commentUsernameInput, commentContentInput;
    private CommentAdapter commentAdapter;
    private List<Comment> comments;
    private HashMap<String, Object> commentHash;

    private DatabaseReference commentRef;  // Reference to comments in Firebase
    private String communityType, timestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detail);

        // Initialize TextViews
        postUsername = findViewById(R.id.username);
        postTimeStamp = findViewById(R.id.timestamp);
        postText = findViewById(R.id.postContent);
        postTitle = findViewById(R.id.postTitle);

        // Get post details from Intent
        String username = getIntent().getStringExtra("username");
        timestamp = getIntent().getStringExtra("timestamp");
        String postContent = getIntent().getStringExtra("postContent");
        String postTitleText = getIntent().getStringExtra("postTitle");
        communityType = getIntent().getStringExtra("type");

        // Display the post data in TextViews
        postUsername.setText(username);
        postTimeStamp.setText(timestamp);
        postText.setText(postContent);
        postTitle.setText(postTitleText);

        // Firebase reference for comments
        commentRef = FirebaseDatabase.getInstance("https://treehole-database-default-rtdb.firebaseio.com/")
                .getReference("posts").child(communityType.toLowerCase()).child(timestamp).child("comments");

        // Initialize ListView for comments
        commentListView = findViewById(R.id.commentListView);
        comments = new ArrayList<>();
        commentHash = new HashMap<>();
        commentAdapter = new CommentAdapter(this, comments);
        commentListView.setAdapter(commentAdapter);

        // Initialize input fields for adding comments
        commentUsernameInput = findViewById(R.id.commentUsername);
        commentContentInput = findViewById(R.id.commentContent);

        // Load comments from Firebase
        loadComments();
    }

    // Method to load comments from Firebase
    private void loadComments() {
        commentRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChildName) {
                String content = snapshot.child("content").getValue(String.class);
                String username = snapshot.child("username").getValue(String.class);
                String timestamp = snapshot.child("timestamp").getValue(String.class);

                if (content != null && username != null && timestamp != null) {
                    Comment comment = new Comment(username, timestamp, content);
                    comments.add(comment);
                    commentAdapter.notifyDataSetChanged();
                    Log.d(TAG, "Comment added: " + content); // Debug log
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, String previousChildName) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, String previousChildName) {}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to load comments: ", error.toException());
            }
        });
    }

    // Method to handle adding a new comment
    public void onPlusClick(View view) {
        String username = commentUsernameInput.getText().toString().trim();
        String content = commentContentInput.getText().toString().trim();

        if (username.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Please enter your nickname and comment.", Toast.LENGTH_SHORT).show();
            return;
        }

        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        Comment newComment = new Comment(username, timestamp, content);
        commentHash.put(timestamp, newComment.getCommentHash());

        // Update Firebase with the new comment
        commentRef.child(timestamp).setValue(newComment.getCommentHash()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                commentUsernameInput.setText("");
                commentContentInput.setText("");
                Toast.makeText(this, "Comment added successfully", Toast.LENGTH_SHORT).show();
            } else {
                Log.e(TAG, "Failed to add comment: ", task.getException());
                Toast.makeText(this, "Failed to add comment", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Additional navigation methods
    public void onProfileClick(View view) {
        navigateTo(ProfilePage.class);
    }

    public void onNotificationClick(View view) {
        navigateTo(NotificationScreen.class);
    }

    public void onHomeClick(View view) {
        navigateTo(Homepage.class);
    }

    public void onExitClick(View view) {
        finish();
    }

    private void navigateTo(Class<?> target) {
        new Handler().postDelayed(() -> startActivity(new Intent(PostDetail.this, target)), 0);
    }
}
