package com.example.treehole;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AcademicScreen extends AppCompatActivity {
    private ListView listView;
    private PostAdapter postAdapter;
    private List<Post> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.academics_page);

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
        if(UserInfo.isFollowingAcademic())
        {
            bell.setImageResource(R.drawable.alertbell);
        }
        else
        {
            bell.setImageResource(R.drawable.bell);
        }

    }

    public void onProfileClick(View view) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(AcademicScreen.this, ProfilePage.class);
            startActivity(intent);
        }, 0);
    }

    public void onHomeClick(View view)
    {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(AcademicScreen.this, Homepage.class);
            startActivity(intent);
        }, 0);
    }

    public void onNotificationClick(View view)
    {

    }

    public void onNotifBellClick(View view)
    {
        ImageButton bell = findViewById(R.id.pushNotifications);
        if(UserInfo.isFollowingAcademic())
        {
            UserInfo.unfollowAcademic();
            bell.setImageResource(R.drawable.bell);
        }
        else
        {
            UserInfo.followAcademic();
            bell.setImageResource(R.drawable.alertbell);
        }
    }
}
