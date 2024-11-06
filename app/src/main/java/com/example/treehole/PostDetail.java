package com.example.treehole;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class PostDetail extends AppCompatActivity {
    private TextView postUsername, postTimeStamp, postText, postTitle;
    private ListView commentListView;
    private EditText  commentContentInput;
    private CommentAdapter commentAdapter;
    private List<Comment> comments;
    private HashMap<String, Object> commentHash;

    private FirebaseDatabase root;
    private DatabaseReference reference;
    private DatabaseReference commentRef;  // Reference to comments in Firebase
    private String communityType, timestamp;
    private Spinner nameSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detail);

        commentListView = findViewById(R.id.commentListView);
        comments = new ArrayList<>();
        commentHash = new HashMap<>();
        commentAdapter = new CommentAdapter(this, comments);
        commentListView.setAdapter(commentAdapter);

        // Initialize TextViews
        postUsername = findViewById(R.id.username);
        postTimeStamp = findViewById(R.id.timestamp);
        postText = findViewById(R.id.postContent);
        postTitle = findViewById(R.id.postTitle);

        // Initialize Spinner
        nameSpinner = findViewById(R.id.commentUsername);
        String name = UserInfo.GetUser();
        String[] nameOptions = {"Option",name, "Anonymous"};
        ArrayAdapter<String> nameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nameOptions);
        nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nameSpinner.setAdapter(nameAdapter);

        commentContentInput = findViewById(R.id.commentContent);

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
        root = FirebaseDatabase.getInstance("https://treehole-database-default-rtdb.firebaseio.com/");

        commentRef = root.getReference("posts").child(communityType.toLowerCase()).child(timestamp).child("comments");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot commentSnapshot: snapshot.getChildren()){
                        String text = commentSnapshot.child("text").getValue(String.class);
                        String username = commentSnapshot.child("username").getValue(String.class);
                        String timestamp = commentSnapshot.child("timestamp").getValue(String.class);
                        Comment comment = new Comment(username,timestamp,text);
                        commentHash.put(timestamp,comment);
                        comments.add(comment);
                    }
                    comments.sort((comment1, comment2) -> comment2.getParsedTimestamp().compareTo(comment1.getParsedTimestamp()));
                    commentAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        };

        commentRef.addListenerForSingleValueEvent(eventListener);
        commentAdapter = new CommentAdapter(this, comments);
        commentListView.setAdapter(commentAdapter);
//

//        // Initialize input fields for adding comments
//        commentUsernameInput = findViewById(R.id.commentUsername);

    }

//    // Method to load comments from Firebase
//    private void loadComments() {
//        Log.d(TAG, "Loading comments from: " + commentRef.toString()); // Log the commentRef path
//
//        commentRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    comments.clear(); // Clear existing comments
//
//                    for (DataSnapshot commentSnapshot : snapshot.getChildren()) {
//                        String content = commentSnapshot.child("content").getValue(String.class);
//                        String username = commentSnapshot.child("username").getValue(String.class);
//                        String timestamp = commentSnapshot.child("timestamp").getValue(String.class);
//
//                        // Log each comment data
//                        Log.d(TAG, "Fetched comment - username: " + username + ", timestamp: " + timestamp + ", content: " + content);
//
//                        if (content != null && username != null && timestamp != null) {
//                            Comment comment = new Comment(username, timestamp, content);
//                            comments.add(comment);
//                        }
//                    }
//
//                    // Sort comments by timestamp in descending order
//                    comments.sort((comment1, comment2) -> comment2.getParsedTimestamp().compareTo(comment1.getParsedTimestamp()));
//
//                    // Notify the adapter of data change
//                    commentAdapter.notifyDataSetChanged();
//                } else {
//                    Log.d(TAG, "No comments found for this post.");
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.w(TAG, "Failed to load comments: ", error.toException());
//            }
//        });
//    }


    // Method to handle adding a new comment
    public void onPlusClick(View view) {
        String username = nameSpinner.getSelectedItem().toString().trim();
        String content = commentContentInput.getText().toString().trim();

        if (username.isEmpty() || "Option".equals(username) || content.isEmpty()) {
            Toast.makeText(this, "Please enter valid nickname and comment.", Toast.LENGTH_SHORT).show();
            return;
        }

        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        Comment newComment = new Comment(username, timestamp, content);
        comments.add(0, newComment);  // Add new comment at the top
        commentHash.put(timestamp, newComment.getCommentHash());

        // Update Firebase with the new comment
        commentRef.child(timestamp).setValue(newComment.getCommentHash()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                commentAdapter.notifyDataSetChanged();
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