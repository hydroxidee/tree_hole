package com.example.treehole;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Post_discussion extends AppCompatActivity {

    private static final String TAG = "Post_discussion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_discussion);

        // Initialize Spinners
        Spinner communitySpinner = findViewById(R.id.community);
        String[] communityOptions = {"Option", "Event", "Academic", "Life"};
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
        Button postButton = findViewById(R.id.post_button);

        // Set the post button's click listener
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString();
                String content = contentEditText.getText().toString();
                String community = communitySpinner.getSelectedItem().toString();
                String name = nameSpinner.getSelectedItem().toString();

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

        // Set the exit button's click listener to navigate back to the corresponding page
        ImageButton exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateBackBasedOnSelection();
            }
        });
    }

    // Method to handle navigation based on community selection
    private void navigateBackBasedOnSelection() {
        Spinner communitySpinner = findViewById(R.id.community);
        String selectedCommunity = communitySpinner.getSelectedItem().toString();
        Log.d(TAG, "Selected Community: " + selectedCommunity);

        Intent intent = null;
        switch (selectedCommunity) {
            case "Event":
                intent = new Intent(Post_discussion.this, EventPage.class);
                break;
            case "Academic":
                intent = new Intent(Post_discussion.this, AcademicPage.class);
                break;
            case "Life":
                intent = new Intent(Post_discussion.this, LifePage.class);
                break;
            default:
                Toast.makeText(this, "Please select a valid community.", Toast.LENGTH_SHORT).show();
                return;
        }

        if (intent != null) {
            startActivity(intent);
            finish(); // Ensure Post_discussion closes properly
        }
    }

    // Override onBackPressed to handle back navigation in case exit button is not used
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        navigateBackBasedOnSelection();
    }
}
