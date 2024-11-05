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
import java.util.Date;
import java.util.List;

public class NotificationScreen extends AppCompatActivity {
    private ListView listView;
    private PostAdapter postAdapter;
    private List<Object> postList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        // Initialize ListView
        listView = findViewById(R.id.postListView);

        // Create sample data for posts
        postList = new ArrayList<>();
        postList.add(new Post("Username1", "2024-11-04 10:00:00", "This is the first sample post."));
        postList.add(new Post("Username2", "2024-11-04 13:00:00", "This is another example of a post."));
        postList.add(new Post("Username3", "2024-11-04 9:55:00", "Here's some sample text for a post."));

        // Set up the adapter and assign it to the ListView
        // Sort the list by timestamp (newest to oldest)
        postList.sort((post1, post2) -> {
            Post post11 = (Post) post1;
            Post post22 = (Post) post2;
            Date date1 = post11.getParsedTimestamp();
            Date date2 = post22.getParsedTimestamp();
            if (date1 == null || date2 == null) {
                return 0; // Handle null dates gracefully
            }
            return date2.compareTo(date1); // Newest first
        });
        postAdapter = new PostAdapter(this, postList);
        listView.setAdapter(postAdapter);

    }

    public void onProfileClick(View view){
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(NotificationScreen.this, ProfilePage.class);
            startActivity(intent);
        }, 0);
    }
    public void onHomeClick(View view){
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(NotificationScreen.this, Homepage.class);
            startActivity(intent);
        }, 0);
    }
}
