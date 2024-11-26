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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

        academicPosts = new ArrayList<>();
        lifePosts = new ArrayList<>();
        eventPosts = new ArrayList<>();
        postList = new ArrayList<>();

        SetAllLists(this::onDataLoaded);

        // Set item click listener to open post details
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (position >= 0 && position < postList.size()) {
                Post selectedPost = postList.get(position);

                // Create an Intent to start PostDetail with post details
                Intent intent = new Intent(NotificationScreen.this, PostDetail.class);
                intent.putExtra("username", selectedPost.getUsername());
                intent.putExtra("timestamp", selectedPost.getTimestamp());
                intent.putExtra("postContent", selectedPost.getPostText());
                intent.putExtra("postTitle", selectedPost.getPostTitle());
                intent.putExtra("type", selectedPost.getCommunityType()); // Type of the post
//hello
                startActivity(intent);
            } else {
                Log.w("NotificationScreen", "Invalid position: " + position);
            }
        });
    }

    private void onDataLoaded() {
        postList.clear();
        long currentTime = System.currentTimeMillis();
        long time24HoursAgo = currentTime - (24 * 60 * 60 * 1000); // 24 hours in milliseconds

        if (UserInfo.isFollowingAcademic()) {
            addPostsToMainList(academicPosts, time24HoursAgo);
        }
        if (UserInfo.isFollowingEvent()) {
            addPostsToMainList(eventPosts, time24HoursAgo);
        }
        if (UserInfo.isFollowingLife()) {
            addPostsToMainList(lifePosts, time24HoursAgo);
        }

        postList.sort((post1, post2) -> post2.getParsedTimestamp().compareTo(post1.getParsedTimestamp()));

        if (postList.isEmpty()) {
            noPostsMessage.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else {
            noPostsMessage.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

            postAdapter = new PostAdapter(this, postList);
            listView.setAdapter(postAdapter);
        }
    }

    @SuppressLint("SetTextI18n")
    public void SetAllLists(Runnable onComplete) {
        root = FirebaseDatabase.getInstance("https://treehole-database-default-rtdb.firebaseio.com/");
        reference = root.getReference();
        DatabaseReference academicRef = reference.child("posts").child("academic");
        DatabaseReference eventRef = reference.child("posts").child("event");
        DatabaseReference lifeRef = reference.child("posts").child("life");

        academicRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String text = postSnapshot.child("text").getValue(String.class);
                    String timestamp = postSnapshot.child("timestamp").getValue(String.class);
                    String username = postSnapshot.child("username").getValue(String.class);
                    String title = postSnapshot.child("title").getValue(String.class);
                    AddAcademicPost(username, timestamp, text, title);
                }

                loadEventPosts(onComplete);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadAcademic:onCancelled", error.toException());
            }
        });
    }

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
                    AddEventPost(username, timestamp, text, title);
                }

                loadLifePosts(onComplete);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadEvent:onCancelled", error.toException());
            }
        });
    }

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
                    AddLifePost(username, timestamp, text, title);
                }

                onComplete.run();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadLife:onCancelled", error.toException());
            }
        });
    }

    private void AddAcademicPost(String user, String time, String text, String title) {
        academicPosts.add(new Post(user, time, text, title, "Academic"));
    }

    private void AddEventPost(String user, String time, String text, String title) {
        eventPosts.add(new Post(user, time, text, title, "Event"));
    }

    private void AddLifePost(String user, String time, String text, String title) {
        lifePosts.add(new Post(user, time, text, title, "Life"));
    }

    private void addPostsToMainList(List<Object> posts, long time24HoursAgo) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        for (Object obj : posts) {
            if (obj instanceof Post) {
                Post post = (Post) obj;
                try {
                    Date postDate = dateFormat.parse(post.getTimestamp());
                    if (postDate != null && postDate.getTime() >= time24HoursAgo) {
                        postList.add(post);
                    }
                } catch (ParseException e) {
                    Log.e(TAG, "Error parsing post timestamp", e);
                }
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