package com.example.treehole;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
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
import java.util.HashMap;
import java.util.List;

public class EventScreen extends AppCompatActivity {
    private ListView listView;
    private PostAdapter postAdapter;
    private HashMap<String, Object> postHash;
    private List<Object> postList;

    private FirebaseDatabase root;
    private DatabaseReference reference;

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
                    postHash.put(timestamp, newPost.getPostHash());
                    postList.add(newPost);

                    // Notify adapter of data change
                    postAdapter.notifyDataSetChanged();

                    //add post to database
                    DatabaseReference screenRef = reference.child("posts").child("event");
                    ValueEventListener eventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //no posts under academic exist
                            if(!dataSnapshot.exists()) {
                                screenRef.setValue(postHash);
                            }
                            else
                            {
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
        setContentView(R.layout.event_page);

        //database initialization
        root = FirebaseDatabase.getInstance("https://treehole-database-default-rtdb.firebaseio.com/");
        reference = root.getReference();

        // Initialize ListView
        listView = findViewById(R.id.postListView);

        postHash = new HashMap<String, Object>();
        postList = new ArrayList<>();

        //gets all academic posts
        DatabaseReference userRef = reference.child("posts").child("event");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    ImageButton bell = findViewById(R.id.pushNotifications);
                    bell.setImageResource(R.drawable.tree);

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        bell.setImageResource(R.drawable.profile);

                        if(UserInfo.isFollowingAcademic())
                        {
                            bell.setImageResource(R.drawable.alertbell);
                        }
                        else
                        {
                            bell.setImageResource(R.drawable.bell);
                        }

                        // Retrieve each post's data
                        String text = postSnapshot.child("text").getValue(String.class);
                        String timestamp = postSnapshot.child("timestamp").getValue(String.class);
                        String username = postSnapshot.child("username").getValue(String.class);

                        makePost(username, timestamp, text);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        };
        userRef.addListenerForSingleValueEvent(eventListener);

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

    public void makePost(String user, String time, String text)
    {
        Post p = new Post(user, time, text);

        postHash.put(time, p);
        postList.add(p);
    }
}
