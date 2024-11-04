package com.example.treehole;

import android.content.Intent;
import android.graphics.Color;
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

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class Homepage extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.homepage);

        //homepage raccoon GIF
        imageView = findViewById(R.id.raccoonImage);
        Glide.with(this).load(R.drawable.raccoon).into(imageView);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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

    }

    //onClick functions for following communities
    public void onFollowAcademicClick(View view){
        if(UserInfo.isFollowingAcademic())
        {

        }
//        //initializing button
//        Button myButton = findViewById(R.id.AcademicButton0);
//        myButton.setClickable(false);
//        myButton.setEnabled(false);
//
//        //if following academic community
//            //set backround color to red
//            myButton.setBackgroundColor(Color.parseColor("#990000"));
//            //set text color to white
//            myButton.setTextColor(Color.parseColor("#FFFFFF"));
    }

    public void onFollowLifeClick(View view){
        if(UserInfo.isFollowingLife())
        {

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

        }
//        //initializing button
//        Button myButton = findViewById(R.id.EventButton0);
//        myButton.setClickable(false);
//        myButton.setEnabled(false);
//
//        //if following event community
//            //set backround color to red
//            myButton.setBackgroundColor(Color.parseColor("#990000"));
//            //set text color to white
//            myButton.setTextColor(Color.parseColor("#FFFFFF"));
    }

    public void onExploreAcademicClick(View view)
    {

    }

    public void onExploreLifeClick(View view)
    {

    }

    public void onExploreEventClick(View view)
    {

    }
}
