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
                    String postTitle = data.getStringExtra("postTitle");

                    // Create a new Post object and add it to the list
                    Post newPost = new Post(username, timestamp,postTitle,postText);
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

        // Initialize post list with sample data
        postList = new ArrayList<>();
        postList.add(new Post("Username1", "2 mins ago", "first.","example"));
        postList.add(new Post("Username2", "5 mins ago", "second.","example"));
        postList.add(new Post("Username3", "10 mins ago", "third.","example"));

        // Set up the adapter and assign it to the ListView
        postAdapter = new PostAdapter(this, postList);
        listView.setAdapter(postAdapter);
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
}
