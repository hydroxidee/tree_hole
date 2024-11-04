package com.example.treehole;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class EventPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_page); // Ensure this layout file exists in res/layout

        // Find the ImageButton by its ID
        ImageButton createButton = findViewById(R.id.createButton);

        // Set up an OnClickListener
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start PostDiscussionActivity
                Intent intent = new Intent(EventPage.this, Post_discussion.class);
                startActivity(intent);
            }
        });
    }
}
