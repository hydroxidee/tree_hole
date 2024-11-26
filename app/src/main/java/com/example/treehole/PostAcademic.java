package com.example.treehole;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PostAcademic extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_academic);
//hello
        Spinner nameSpinner = findViewById(R.id.name);
        String username = UserInfo.GetUser();
        String[] nameOptions = {"Option", username, "Anonymous"};
        ArrayAdapter<String> nameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nameOptions);
        nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nameSpinner.setAdapter(nameAdapter);
    }

    public void onPostClick(View view) {
        EditText titleEditText = findViewById(R.id.title);
        EditText contentEditText = findViewById(R.id.content);
        Spinner postAsSpinner = findViewById(R.id.name);


        String title = titleEditText.getText().toString().trim();
        String content = contentEditText.getText().toString().trim();
        String postAs = postAsSpinner.getSelectedItem().toString();

        if (title.isEmpty() || content.isEmpty() || postAs == "Option") {
            Toast.makeText(PostAcademic.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        } else if (content.length() > 1000)
        {
            Toast.makeText(PostAcademic.this, "Content too long (under 1000 characters)", Toast.LENGTH_SHORT).show();
            return;
        }

        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        Intent resultIntent = new Intent();
        resultIntent.putExtra("username", postAs);
        resultIntent.putExtra("timestamp", timestamp);
        resultIntent.putExtra("postTitle",title);
        resultIntent.putExtra("postContent",content);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void onExitClick(View view) {
        finish();
    }
}