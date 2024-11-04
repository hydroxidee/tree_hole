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
    public void onClickAcademic(View view){
        //initializing button
        Button myButton = findViewById(R.id.AcademicButton0);
        myButton.setClickable(false);
        myButton.setEnabled(false);

        //if following academic community
            //set backround color to red
            myButton.setBackgroundColor(Color.parseColor("#990000"));
            //set text color to white
            myButton.setTextColor(Color.parseColor("#FFFFFF"));
    }

    public void onClickLife(View view){
        //initializing button
        Button myButton = findViewById(R.id.LifeButton0);
        myButton.setClickable(false);
        myButton.setEnabled(false);

        //if following life community
            //set backround color to red
            myButton.setBackgroundColor(Color.parseColor("#990000"));
            //set text color to white
            myButton.setTextColor(Color.parseColor("#FFFFFF"));
    }

    public void onClickEvent(View view){
        //initializing button
        Button myButton = findViewById(R.id.EventButton0);
        myButton.setClickable(false);
        myButton.setEnabled(false);

        //if following event community
            //set backround color to red
            myButton.setBackgroundColor(Color.parseColor("#990000"));
            //set text color to white
            myButton.setTextColor(Color.parseColor("#FFFFFF"));
    }


}
