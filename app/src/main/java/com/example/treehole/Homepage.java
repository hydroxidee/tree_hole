package com.example.treehole;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.metrics.Event;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Homepage extends AppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.homepage);

        //homepage raccoon GIF
        ImageView imageView = findViewById(R.id.raccoonImage);
        Glide.with(this).load(R.drawable.raccoon).into(imageView);

        //homepage raccoon speech bubble customization
        TextView speechBubble = findViewById(R.id.speechBubbleText);
        speechBubble.setText("Hi " + UserInfo.getFirstName() + "!");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button academicButton = findViewById(R.id.AcademicButton0);
        if(UserInfo.isFollowingAcademic())
        {
            academicButton.setClickable(true);
            academicButton.setEnabled(true);

            academicButton.setBackgroundColor(Color.parseColor("#990000"));
            academicButton.setTextColor(Color.parseColor("#FFFFFF"));
        }
        else
        {
            academicButton.setClickable(false);
            academicButton.setEnabled(false);

            academicButton.setBackgroundColor(Color.parseColor("#F7F3AD"));
            academicButton.setTextColor(Color.parseColor("#F7F3AD"));
        }
//hello
        Button lifeButton = findViewById(R.id.LifeButton0);
        if(UserInfo.isFollowingLife())
        {
            lifeButton.setClickable(true);
            lifeButton.setEnabled(true);

            lifeButton.setBackgroundColor(Color.parseColor("#990000"));
            lifeButton.setTextColor(Color.parseColor("#FFFFFF"));
        }
        else
        {
            lifeButton.setClickable(false);
            lifeButton.setEnabled(false);

            lifeButton.setBackgroundColor(Color.parseColor("#F7F3AD"));
            lifeButton.setTextColor(Color.parseColor("#F7F3AD"));
        }

        Button eventButton = findViewById(R.id.EventButton0);
        if(UserInfo.isFollowingEvent())
        {
            eventButton.setClickable(true);
            eventButton.setEnabled(true);

            eventButton.setBackgroundColor(Color.parseColor("#990000"));
            eventButton.setTextColor(Color.parseColor("#FFFFFF"));
        }
        else
        {
            eventButton.setClickable(false);
            eventButton.setEnabled(false);

            eventButton.setBackgroundColor(Color.parseColor("#F7F3AD"));
            eventButton.setTextColor(Color.parseColor("#F7F3AD"));
        }


    }

    // takes user to profile page
    public void onProfileClick(View view) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(Homepage.this, ProfilePage.class);
            startActivity(intent);
        }, 0);
    }

    public void onHomeClick(View view)
    {

    }

    public void onNotificationClick(View view)
    {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(Homepage.this, NotificationScreen.class);
            startActivity(intent);
        }, 0);
    }

    //onClick functions for following communities
    public void onFollowAcademicClick(View view){
        if(UserInfo.isFollowingAcademic())
        {
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                Intent intent = new Intent(Homepage.this, AcademicScreen.class);
                startActivity(intent);
            }, 0);
        }
    }

    public void onFollowLifeClick(View view){
        if(UserInfo.isFollowingLife())
        {
            Handler handler = new Handler();

            handler.postDelayed(() -> {
                Intent intent = new Intent(Homepage.this, LifeScreen.class);
                startActivity(intent);
            }, 0);
        }
    }

    public void onFollowEventClick(View view){
        if(UserInfo.isFollowingEvent())
        {
            Handler handler = new Handler();

            handler.postDelayed(() -> {
                Intent intent = new Intent(Homepage.this, EventScreen.class);
                startActivity(intent);
            }, 0);
        }
    }

    public void onExploreAcademicClick(View view)
    {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(Homepage.this, AcademicScreen.class);
            startActivity(intent);
        }, 0);
    }

    public void onExploreLifeClick(View view)
    {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(Homepage.this, LifeScreen.class);
            startActivity(intent);
        }, 0);
    }

    public void onExploreEventClick(View view)
    {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(Homepage.this, EventScreen.class);
            startActivity(intent);
        }, 0);
    }
    public void OnNotificationPage(View view){
        Handler handler = new Handler();
        handler.postDelayed(()->{
            Intent intent = new Intent(Homepage.this, NotificationScreen.class);
            startActivity(intent);
        },0);
    }
}