package com.example.treehole;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class PostDetail extends AppCompatActivity {
    private TextView postUsername;
    private TextView postTimeStamp;
    private TextView postText;
    private ListView commentListView;
    private CommentAdapter commentAdapter;
    private List<Comment> commentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detail);

        // Initialize the TextViews inside post_item layout
        postUsername = findViewById(R.id.username);
        postTimeStamp = findViewById(R.id.timestamp);
        postText = findViewById(R.id.postContent);

        // Get post data from intent
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String timestamp = intent.getStringExtra("timestamp");
        String text = intent.getStringExtra("postText");

        // Set the post data to TextViews
        postUsername.setText(username);
        postTimeStamp.setText(timestamp);
        postText.setText(text);

        // Initialize ListView for comments and set adapter
        commentListView = findViewById(R.id.commentListView);
        commentList = new ArrayList<>();
        commentList.add(new Comment("Username1", "2024-11-04 10:00:00", "This is the first sample post."));
        commentList.add(new Comment("Username2", "2024-11-04 11:00:00", "This is the second sample post."));
        commentList.add(new Comment("Username3", "2024-11-04 12:00:00", "This is the third sample post."));

        // Populate commentList with sample comments or from a database
        commentAdapter = new CommentAdapter(this, commentList);
        commentListView.setAdapter(commentAdapter);
    }
    public void onProfileClick(View view){
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(PostDetail.this, ProfilePage.class);
            startActivity(intent);
        }, 0);
    }
    public void onNotificationClick(View view){
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(PostDetail.this, NotificationScreen.class);
            startActivity(intent);
        }, 0);
    }
    public void onHomeClick(View view){
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(PostDetail.this, Homepage.class);
            startActivity(intent);
        }, 0);
    }
}
