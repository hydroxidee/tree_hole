package com.example.treehole;

import static android.content.ContentValues.TAG;

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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class LifeScreen extends AppCompatActivity {
    private ListView listView;
    private PostAdapter postAdapter;
    private HashMap<String, Object> postHash;
    public static List<Post> lifePostList = new ArrayList<>();

    private FirebaseDatabase root;
    private DatabaseReference reference;

    // Define ActivityResultLauncher to handle result from PostAcademic
    private final ActivityResultLauncher<Intent> postLifeLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    String username = data.getStringExtra("username");
                    String timestamp = data.getStringExtra("timestamp");
                    String postText = data.getStringExtra("postText");

                    // Create a new Post object and add it to the list
                    Post newPost = new Post(username, timestamp, postText, "Life");
                    postHash.put(timestamp, newPost.getPostHash());
                    lifePostList.add(newPost);
                    lifePostList.sort((post1, post2) -> post2.getParsedTimestamp().compareTo(post1.getParsedTimestamp()));
                    // Notify adapter of data change
                    postAdapter.notifyDataSetChanged();

                    // Add post to database
                    DatabaseReference screenRef = reference.child("posts").child("life");
                    ValueEventListener eventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // No posts under life exist
                            if (!dataSnapshot.exists()) {
                                screenRef.setValue(postHash);
                            } else {
                                screenRef.updateChildren(postHash);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.w(TAG, "loadPost:onCancelled", error.toException());
                        }
                    };
                    screenRef.addListenerForSingleValueEvent(eventListener);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.life_page);

        // Database initialization
        root = FirebaseDatabase.getInstance("https://treehole-database-default-rtdb.firebaseio.com/");
        reference = root.getReference();

        // Initialize ListView
        listView = findViewById(R.id.postListView);

        postHash = new HashMap<>();
        lifePostList = new ArrayList<>();

        // Get all life posts
        DatabaseReference userRef = reference.child("posts").child("life");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ImageButton bell = findViewById(R.id.pushNotifications);
                    bell.setImageResource(R.drawable.tree);

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        bell.setImageResource(R.drawable.profile);

                        if (UserInfo.isFollowingLife()) {
                            bell.setImageResource(R.drawable.alertbell);
                        } else {
                            bell.setImageResource(R.drawable.bell);
                        }

                        // Retrieve each post's data
                        String text = postSnapshot.child("text").getValue(String.class);
                        String timestamp = postSnapshot.child("timestamp").getValue(String.class);
                        String username = postSnapshot.child("username").getValue(String.class);

                        makePost(username, timestamp, text);
                    }
                    // Sort postList by timestamp in descending order (most recent first)
                    lifePostList.sort((post1, post2) -> post2.getParsedTimestamp().compareTo(post1.getParsedTimestamp()));

                    // Notify adapter of data change after sorting
                    postAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        };

        userRef.addListenerForSingleValueEvent(eventListener);
        // Set up the adapter and assign it to the ListView
        postAdapter = new PostAdapter(this, Collections.singletonList(lifePostList));
        listView.setAdapter(postAdapter);

        // Set item click listener to open post details
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Log.d("LifeScreen", "Clicked post at position: " + position);

            if (position >= 0 && position < lifePostList.size()) {
                Intent intent = new Intent(LifeScreen.this, PostDetail.class);
                intent.putExtra("postIndex", position);
                startActivity(intent);
            } else {
                Log.w("LifeScreen", "Invalid position: " + position);
            }
        });
    }

    public void onPlusClick(View view) {
        Intent intent = new Intent(LifeScreen.this, PostLife.class);
        postLifeLauncher.launch(intent);  // Launch PostAcademic with the launcher
    }

    public void onProfileClick(View view) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(LifeScreen.this, ProfilePage.class);
            startActivity(intent);
        }, 0);
    }

    public void onHomeClick(View view) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(LifeScreen.this, Homepage.class);
            startActivity(intent);
        }, 0);
    }

    public void onNotifBellClick(View view) {
        ImageButton bell = findViewById(R.id.pushNotifications);
        if (UserInfo.isFollowingLife()) {
            UserInfo.unfollowLife();
            bell.setImageResource(R.drawable.bell);
        } else {
            UserInfo.followLife();
            bell.setImageResource(R.drawable.alertbell);
        }
    }

    public void onNotificationClick(View view) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(LifeScreen.this, NotificationScreen.class);
            startActivity(intent);
        }, 0);
    }

    public void makePost(String user, String time, String text) {
        Post p = new Post(user, time, text, "Life");

        postHash.put(time, p);
        lifePostList.add(p);
    }
}
