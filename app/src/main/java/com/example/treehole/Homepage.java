package com.example.treehole;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Homepage {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_end_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button playButton = findViewById(R.id.button01);
        playButton.setTextColor(Color.parseColor("#FFFFFF"));

        Intent intent = getIntent();
        int endTime = intent.getIntExtra("endTime", 0);
        boolean endStatus = intent.getBooleanExtra("win", true);
        String timeMessage = "Used " + String.valueOf(endTime) + " seconds.";
        String gameStatusMessage = "";
        String lastMessage = "";

        if(endStatus){
            gameStatusMessage = "You win.";
            lastMessage = "Good job!";
        } else{
            gameStatusMessage = "You lose.";
            lastMessage = "Nice try.";
        }

        TextView updateTime = (TextView) findViewById(R.id.endMessage01);
        updateTime.setText(timeMessage);

        TextView updateGameStatus = (TextView) findViewById(R.id.endMessage02);
        updateGameStatus.setText(gameStatusMessage);

        TextView updateLastMessage = (TextView) findViewById(R.id.endMessage03);
        updateLastMessage.setText(lastMessage);
    }

}
