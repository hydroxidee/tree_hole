package com.example.treehole;

import android.content.Intent;
import android.graphics.Color;
import android.media.metrics.Event;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.homepage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button myAcademicButton = findViewById(R.id.AcademicButton0);
        if(UserInfo.isFollowingAcademic())
        {
            myAcademicButton.setClickable(true);
            myAcademicButton.setEnabled(true);

            myAcademicButton.setBackgroundColor(Color.parseColor("#990000"));
            myAcademicButton.setTextColor(Color.parseColor("#FFFFFF"));
        }
        else
        {
            myAcademicButton.setClickable(false);
            myAcademicButton.setEnabled(false);

            myAcademicButton.setBackgroundColor(Color.parseColor("#F7F3AD"));
            myAcademicButton.setTextColor(Color.parseColor("#F7F3AD"));
        }

        Button myEventButton = findViewById(R.id.EventButton0);
        if(UserInfo.isFollowingEvent())
        {
            myEventButton.setClickable(true);
            myEventButton.setEnabled(true);

            myEventButton.setBackgroundColor(Color.parseColor("#990000"));
            myEventButton.setTextColor(Color.parseColor("#FFFFFF"));
        }
        else
        {
            myEventButton.setClickable(false);
            myEventButton.setEnabled(false);

            myEventButton.setBackgroundColor(Color.parseColor("#F7F3AD"));
            myEventButton.setTextColor(Color.parseColor("#F7F3AD"));
        }

        Button myLifeButton = findViewById(R.id.LifeButton0);
        if(UserInfo.isFollowingLife())
        {
            myLifeButton.setClickable(true);
            myLifeButton.setEnabled(true);

            myLifeButton.setBackgroundColor(Color.parseColor("#990000"));
            myLifeButton.setTextColor(Color.parseColor("#FFFFFF"));
        }
        else
        {
            myLifeButton.setClickable(false);
            myLifeButton.setEnabled(false);

            myLifeButton.setBackgroundColor(Color.parseColor("#F7F3AD"));
            myLifeButton.setTextColor(Color.parseColor("#F7F3AD"));
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
            handler.postDelayed(()->{
                Intent intent = new Intent(Homepage.this, LifeScreen.class);
                startActivity(intent);
            },0);
        }

//        //initializing button
//        Button myButton = findViewById(R.id.LifeButton0);
//        myButton.setClickable(false);
//        myButton.setEnabled(false);
//
//        //if following life community
//            //set backround color to red
//            myButton.setBackgroundColor(Color.parseColor("#990000"));
//            //set text color to white
//            myButton.setTextColor(Color.parseColor("#FFFFFF"));
    }

    public void onFollowEventClick(View view){
        if(UserInfo.isFollowingEvent())
        {
            Handler handler = new Handler();
            handler.postDelayed(()->{
                Intent intent = new Intent(Homepage.this, EventScreen.class);
                startActivity(intent);
            },0);
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
