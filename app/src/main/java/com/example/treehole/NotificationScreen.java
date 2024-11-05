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
    private List<Object> postList;

    private  List<Object> academicPosts;
    private  List<Object> lifePosts;
    private  List<Object> eventPosts;

    private  FirebaseDatabase root;
    private  DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.notification);
        SetAllLists();

        // Initialize ListView
        listView = findViewById(R.id.postListView);


//        // Create sample data for posts
//        postList = new ArrayList<>();
//        postList.add(new Post("Username1", "2024-11-04 10:00:00", "This is the first sample post."));
//        postList.add(new Post("Username2", "2024-11-04 13:00:00", "This is another example of a post."));
//        postList.add(new Post("Username3", "2024-11-04 9:55:00", "Here's some sample text for a post."));
//
//        // Set up the adapter and assign it to the ListView
//        // Sort the list by timestamp (newest to oldest)
//        postList.sort((post1, post2) -> {
//            Post post11 = (Post) post1;
//            Post post22 = (Post) post2;
//            Date date1 = post11.getParsedTimestamp();
//            Date date2 = post22.getParsedTimestamp();
//            if (date1 == null || date2 == null) {
//                return 0; // Handle null dates gracefully
//            }
//            return date2.compareTo(date1); // Newest first
//        });
//        postAdapter = new PostAdapter(this, postList);
//        listView.setAdapter(postAdapter);


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

    @SuppressLint("SetTextI18n")
    public  void SetAllLists() {
        root = FirebaseDatabase.getInstance("https://treehole-database-default-rtdb.firebaseio.com/");
        reference = root.getReference();

        academicPosts = new ArrayList<>();
        lifePosts = new ArrayList<>();
        eventPosts = new ArrayList<>();

        DatabaseReference userRef = reference.child("posts").child("academic");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ImageButton bell = findViewById(R.id.notificationButton);
                    bell.setImageResource(R.drawable.tree);
                    bell.setImageResource(R.drawable.profile);
                    bell.setImageResource(R.drawable.tree);
                    bell.setImageResource(R.drawable.profile);
                    bell.setImageResource(R.drawable.tree);
                    bell.setImageResource(R.drawable.profile);


                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        // Retrieve each post's data
                        String text = postSnapshot.child("text").getValue(String.class);
                        String timestamp = postSnapshot.child("timestamp").getValue(String.class);
                        String username = postSnapshot.child("username").getValue(String.class);

                        AddAcademicPost(username, timestamp, text);

                        TextView temp = findViewById(R.id.titleText);
                        temp.setText(academicPosts.toString());

                        temp.setText("Notifications");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        };
        userRef.addListenerForSingleValueEvent(eventListener);

        userRef = reference.child("posts").child("event");

        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ImageButton bell = findViewById(R.id.notificationButton);
                    bell.setImageResource(R.drawable.tree);
                    bell.setImageResource(R.drawable.profile);
                    bell.setImageResource(R.drawable.tree);
                    bell.setImageResource(R.drawable.profile);
                    bell.setImageResource(R.drawable.tree);
                    bell.setImageResource(R.drawable.profile);


                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        // Retrieve each post's data
                        String text = postSnapshot.child("text").getValue(String.class);
                        String timestamp = postSnapshot.child("timestamp").getValue(String.class);
                        String username = postSnapshot.child("username").getValue(String.class);

                        AddEventPost(username, timestamp, text);

                        TextView temp = findViewById(R.id.titleText);
                        temp.setText(eventPosts.toString());

                        temp.setText("Notifications");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        };
        userRef.addListenerForSingleValueEvent(eventListener);

        userRef = reference.child("posts").child("life");

        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ImageButton bell = findViewById(R.id.notificationButton);
                    bell.setImageResource(R.drawable.tree);
                    bell.setImageResource(R.drawable.profile);
                    bell.setImageResource(R.drawable.tree);
                    bell.setImageResource(R.drawable.profile);
                    bell.setImageResource(R.drawable.tree);
                    bell.setImageResource(R.drawable.profile);


                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        // Retrieve each post's data
                        String text = postSnapshot.child("text").getValue(String.class);
                        String timestamp = postSnapshot.child("timestamp").getValue(String.class);
                        String username = postSnapshot.child("username").getValue(String.class);

                        AddLifePost(username, timestamp, text);

                        TextView temp = findViewById(R.id.titleText);
                        temp.setText(lifePosts.toString());

                        temp.setText("Notifications");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        };
        userRef.addListenerForSingleValueEvent(eventListener);
    }

    private void AddAcademicPost(String user, String time, String text)
    {
        academicPosts.add(new Post(user, time, text));
    }

    private void AddEventPost(String user, String time, String text)
    {
        eventPosts.add(new Post(user, time, text));
    }

    private void AddLifePost(String user, String time, String text)
    {
        lifePosts.add(new Post(user, time, text));
    }

}
