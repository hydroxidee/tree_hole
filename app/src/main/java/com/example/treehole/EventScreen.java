package com.example.treehole;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class EventScreen extends AppCompatActivity {
    private ListView listView;
    private PostAdapter postAdapter;
    private List<Post> postList;

    // Define ActivityResultLauncher to handle result from PostAcademic
    private final ActivityResultLauncher<Intent> postAcademicLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    String username = data.getStringExtra("username");
                    String timestamp = data.getStringExtra("timestamp");
                    String postText = data.getStringExtra("postText");

                    // Create a new Post object and add it to the list
                    Post newPost = new Post(username, timestamp,postText);
                    postList.add(newPost);

                    // Notify adapter of data change
                    postAdapter.notifyDataSetChanged();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_page);

        // Initialize ListView
        listView = findViewById(R.id.postListView);

        // Create sample data for posts
        postList = new ArrayList<>();
        postList.add(new Post("Username1", "2 mins ago", "This is the first sample post."));
        postList.add(new Post("Username2", "5 mins ago", "This is another example of a post."));
        postList.add(new Post("Username3", "10 mins ago", "Here's some sample text for a post."));

        // Set up the adapter and assign it to the ListView
        postAdapter = new PostAdapter(this, postList);
        listView.setAdapter(postAdapter);

        // set notif bell
        ImageButton bell = findViewById(R.id.pushNotifications);
        if(UserInfo.isFollowingEvent())
        {
            bell.setImageResource(R.drawable.alertbell);
        }
        else
        {
            bell.setImageResource(R.drawable.bell);
        }

        // Set an item click listener to open post details
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Post selectedPost = postList.get(position);

            // Create an intent to start PostDetailActivity and pass the post details
            Intent intent = new Intent(EventScreen.this, PostDetail.class);
            intent.putExtra("username", selectedPost.getUsername());
            intent.putExtra("timestamp", selectedPost.getTimestamp());
            intent.putExtra("postText", selectedPost.getText());

            startActivity(intent);
        });
    }

    public void onPlusClick(View view) {
        Intent intent = new Intent(EventScreen.this, PostEvent.class);
        postAcademicLauncher.launch(intent);  // Launch PostAcademic with the launcher
    }
    public void onProfileClick(View view){
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(EventScreen.this, ProfilePage.class);
            startActivity(intent);
        }, 0);
    }
    public void onHomeClick(View view){
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(EventScreen.this, Homepage.class);
            startActivity(intent);
        }, 0);
    }

    public void onNotifBellClick(View view)
    {
        ImageButton bell = findViewById(R.id.pushNotifications);
        if(UserInfo.isFollowingEvent())
        {
            UserInfo.unfollowEvent();
            bell.setImageResource(R.drawable.bell);
        }
        else
        {
            UserInfo.followEvent();
            bell.setImageResource(R.drawable.alertbell);
        }
    }
    public void onNotificationClick(View view){
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(EventScreen.this, NotificationScreen.class);
            startActivity(intent);
        }, 0);
    }
}
