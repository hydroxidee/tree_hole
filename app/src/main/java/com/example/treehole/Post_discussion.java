package com.example.treehole;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Post_discussion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_discussion);

        // Initialize Spinners
        Spinner communitySpinner = findViewById(R.id.community);
        String[] communityOptions = {"Option", "Event", "Personal", "Life"};
        ArrayAdapter<String> communityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, communityOptions);
        communityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        communitySpinner.setAdapter(communityAdapter);

        Spinner nameSpinner = findViewById(R.id.name);
        String[] nameOptions = {"Option", "Placeholder", "Anonymous"};
        ArrayAdapter<String> nameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nameOptions);
        nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nameSpinner.setAdapter(nameAdapter);

        // Retrieve EditText and Button components
        EditText titleEditText = findViewById(R.id.title);
        EditText contentEditText = findViewById(R.id.content);
        Button postButton = findViewById(R.id.post_button); // Assuming you have set the button's id to "post_button"

        // Set the button's click listener
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the values entered by the user
                String title = titleEditText.getText().toString();
                String content = contentEditText.getText().toString();
                String community = communitySpinner.getSelectedItem().toString();
                String name = nameSpinner.getSelectedItem().toString();

                // Create and display the AlertDialog with the entered information
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Post_discussion.this);
                dialogBuilder.setTitle("Post Details");
                dialogBuilder.setMessage("Title: " + title + "\n\n" +
                        "Community: " + community + "\n\n" +
                        "Content: " + content + "\n\n" +
                        "Post As: " + name);
                dialogBuilder.setPositiveButton("OK", null);
                dialogBuilder.show();
            }
        });
    }
}
