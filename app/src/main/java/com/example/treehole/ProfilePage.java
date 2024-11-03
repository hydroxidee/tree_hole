package com.example.treehole;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

public class ProfilePage extends AppCompatActivity {
    private FirebaseDatabase root;
    private DatabaseReference reference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.profile_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        root = FirebaseDatabase.getInstance("https://treehole-database-default-rtdb.firebaseio.com/");
        reference = root.getReference();

        DatabaseReference userRef = reference.child("users").child(UserInfo.GetUser());

        ValueEventListener eventListener = new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    HashMap<String, String> info = (HashMap<String, String>) dataSnapshot.getValue();
                    TextView name = findViewById(R.id.profileName);
                    TextView IDNum = findViewById(R.id.IDNum);
                    TextView role = findViewById(R.id.roleType);
                    TextView email = findViewById(R.id.email);
                    TextView bio = findViewById(R.id.bio);

                    name.setText(info.get("first") + " " + info.get("last"));
                    IDNum.setText(info.get("ID"));
                    role.setText(info.get("role"));
                    email.setText(info.get("username") + "@usc.edu");

                    if(!info.get("bio").isEmpty())
                    {
                        bio.setText(info.get("bio"));
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

    public void onHomeClick(View view) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(ProfilePage.this, Homepage.class);
            startActivity(intent);
        }, 0);
    }
}
