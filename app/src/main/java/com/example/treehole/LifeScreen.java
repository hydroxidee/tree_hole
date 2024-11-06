package com.example.treehole;

import static android.content.ContentValues.TAG;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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
                    String postText = data.getStringExtra("postContent");
                    String postTitle = data.getStringExtra("postTitle");

                    // Create a new Post object and add it to the list
                    Post newPost = new Post(username, timestamp, postText,postTitle,"Life");
                    postHash.put(timestamp, newPost.getPostHash());
                    lifePostList.add(newPost);
                    lifePostList.sort((post1, post2) -> post2.getParsedTimestamp().compareTo(post1.getParsedTimestamp()));
                    // Notify adapter of data change
                    postAdapter.notifyDataSetChanged();

                    //add post to database
                    DatabaseReference screenRef = reference.child("posts").child("life");
                    ValueEventListener eventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //no posts under life exist
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
        setContentView(R.layout.life_page);

        //database initialization
        root = FirebaseDatabase.getInstance("https://treehole-database-default-rtdb.firebaseio.com/");
        reference = root.getReference();

        // Initialize ListView
        listView = findViewById(R.id.postListView);

        postHash = new HashMap<String, Object>();
        lifePostList = new ArrayList<>();

        //gets all academic posts
        DatabaseReference userRef = reference.child("posts").child("life");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    ImageButton bell = findViewById(R.id.pushNotifications);
                    bell.setImageResource(R.drawable.tree);

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        bell.setImageResource(R.drawable.profile);

                        if(UserInfo.isFollowingLife())
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
                        String title = postSnapshot.child("title").getValue(String.class);
                        makePost(username, timestamp, text, title);
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
        postAdapter = new PostAdapter(this, lifePostList);
        listView.setAdapter(postAdapter);



        listView.setOnItemClickListener((parent, view, position, id) -> {
            Log.d("AcademicScreen", "Clicked post at position: " + position);

            if (position >= 0 && position < lifePostList.size()) {
                Post selectedPost = lifePostList.get(position);

                Intent intent = new Intent(LifeScreen.this, PostDetail.class);
                intent.putExtra("postIndex", position);
                intent.putExtra("type", selectedPost.getCommunityType());
                intent.putExtra("username",selectedPost.getUsername());
                intent.putExtra("timestamp", selectedPost.getTimestamp());
                intent.putExtra("postContent", selectedPost.getPostText());
                intent.putExtra("postTitle", selectedPost.getPostTitle());
                startActivity(intent);
            } else {
                Log.w("lifeScreen", "Invalid position: " + position);
            }
        });
    }

    public void onPlusClick(View view) {
        Intent intent = new Intent(LifeScreen.this, PostLife.class);
        postLifeLauncher.launch(intent);  // Launch PostAcademic with the launcher
        if(UserInfo.isFollowingLife())
            showLifeNotification();
    }
    private void showLifeNotification() {
        String channelId = "life_posts_channel";
        String channelName = "Life Posts";
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Create the notification channel if running on Android 8.0 or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.alertbell) // Replace with your app's notification icon
                .setContentTitle("Life")
                .setContentText("Life has a new post")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        // Display the notification
        notificationManager.notify(1, builder.build());
    }
    //hello
        public void onProfileClick(View view){
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(LifeScreen.this, ProfilePage.class);
            startActivity(intent);
        }, 0);
    }
    public void onHomeClick(View view){
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(LifeScreen.this, Homepage.class);
            startActivity(intent);
        }, 0);
    }

    public void onNotifBellClick(View view)
    {
        ImageButton bell = findViewById(R.id.pushNotifications);
        if(UserInfo.isFollowingLife())
        {
            UserInfo.unfollowLife();
            bell.setImageResource(R.drawable.bell);
        }
        else
        {
            UserInfo.followLife();
            bell.setImageResource(R.drawable.alertbell);
        }
    }
    public void onNotificationClick(View view){
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(LifeScreen.this, NotificationScreen.class);
            startActivity(intent);
        }, 0);
    }

    public void makePost(String user, String time, String text,String title)
    {
        Post p = new Post(user, time, text,title,"Life");

        postHash.put(time, p);
        lifePostList.add(p);
    }

}