package com.example.treehole;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationScreen extends AppCompatActivity {
    private ListView listView;
    private PostAdapter postAdapter;
    private List<Post> postList;
    private List<Object> academicPosts;
    private List<Object> lifePosts;
    private List<Object> eventPosts;
    private FirebaseDatabase root;
    private DatabaseReference reference;
    private TextView noPostsMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        listView = findViewById(R.id.postListView);
        noPostsMessage = findViewById(R.id.noPostsMessage); // Initialize the noPostsMessage TextView

        // Initialize post lists
        academicPosts = new ArrayList<>();
        lifePosts = new ArrayList<>();
        eventPosts = new ArrayList<>();
        postList = new ArrayList<>();

        // Call SetAllLists and handle the data once loaded
        SetAllLists(this::onDataLoaded);
    }

    // Callback method when data is loaded
    private void onDataLoaded() {
        // Clear main postList and add posts according to user subscriptions
        postList.clear();

        if (UserInfo.isFollowingAcademic()) {
            addPostsToMainList(academicPosts);
        }
        if (UserInfo.isFollowingEvent()) {
            addPostsToMainList(eventPosts);
        }
        if (UserInfo.isFollowingLife()) {
            addPostsToMainList(lifePosts);
        }

        // Sort postList by timestamp (newest first)
        postList.sort((post1, post2) -> post2.getParsedTimestamp().compareTo(post1.getParsedTimestamp()));

        // Check if there are posts to display
        if (postList.isEmpty()) {
            // If postList is empty, show noPostsMessage and hide listView
            noPostsMessage.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else {
            // If there are posts, show listView and hide noPostsMessage
            noPostsMessage.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

            // Set up the adapter and display posts
            postAdapter = new PostAdapter(this, postList);
            listView.setAdapter(postAdapter);
        }
    }

    // Update SetAllLists to accept a callback
    @SuppressLint("SetTextI18n")
    public void SetAllLists(Runnable onComplete) {
        root = FirebaseDatabase.getInstance("https://treehole-database-default-rtdb.firebaseio.com/");
        reference = root.getReference();
        DatabaseReference academicRef = reference.child("posts").child("academic");
        DatabaseReference eventRef = reference.child("posts").child("event");
        DatabaseReference lifeRef = reference.child("posts").child("life");

        // Load academic posts
        academicRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String text = postSnapshot.child("text").getValue(String.class);
                    String timestamp = postSnapshot.child("timestamp").getValue(String.class);
                    String username = postSnapshot.child("username").getValue(String.class);
                    String title = postSnapshot.child("title").getValue(String.class);
                    AddAcademicPost(username, timestamp, text,title);
                }

                // Load event posts after academic posts
                loadEventPosts(onComplete);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadAcademic:onCancelled", error.toException());
            }
        });
    }

    // Helper method to load event posts
    private void loadEventPosts(Runnable onComplete) {
        DatabaseReference eventRef = reference.child("posts").child("event");
        eventRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String text = postSnapshot.child("text").getValue(String.class);
                    String timestamp = postSnapshot.child("timestamp").getValue(String.class);
                    String username = postSnapshot.child("username").getValue(String.class);
                    String title = postSnapshot.child("title").getValue(String.class);
                    AddEventPost(username, timestamp, text,title);
                }

                // Load life posts after event posts
                loadLifePosts(onComplete);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadEvent:onCancelled", error.toException());
            }
        });
    }

    // Helper method to load life posts
    private void loadLifePosts(Runnable onComplete) {
        DatabaseReference lifeRef = reference.child("posts").child("life");
        lifeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String text = postSnapshot.child("text").getValue(String.class);
                    String timestamp = postSnapshot.child("timestamp").getValue(String.class);
                    String username = postSnapshot.child("username").getValue(String.class);
                    String title = postSnapshot.child("title").getValue(String.class);
                    AddLifePost(username, timestamp, text,title);
                }

                // Notify that all posts are loaded
                onComplete.run();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadLife:onCancelled", error.toException());
            }
        });
    }

    private void AddAcademicPost(String user, String time, String text,String title) {
        academicPosts.add(new Post(user, time, text, title,"Academic"));
    }

    private void AddEventPost(String user, String time, String text, String title) {
        eventPosts.add(new Post(user, time, text,title, "Event"));
    }

    private void AddLifePost(String user, String time, String text, String title) {
        lifePosts.add(new Post(user, time, text,title, "Life"));
    }

    private void addPostsToMainList(List<Object> posts) {
        for (Object obj : posts) {
            if (obj instanceof Post) {
                postList.add((Post) obj);
            }
        }
    }

    public void onProfileClick(View view) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(NotificationScreen.this, ProfilePage.class);
            startActivity(intent);
        }, 0);
    }

    public void onHomeClick(View view) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(NotificationScreen.this, Homepage.class);
            startActivity(intent);
        }, 0);
    }
}
