package com.example.treehole;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    //
    private FirebaseDatabase root;
    private DatabaseReference reference;


    EditText userInput;
    EditText passInput;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        root = FirebaseDatabase.getInstance("https://treehole-database-default-rtdb.firebaseio.com/");
        reference = root.getReference();

        int update = getIntent().getIntExtra("update", -1);
        TextView updateMsg = findViewById(R.id.updateMessage);

        if(update == -1) {
            updateMsg.setText("");
        }
        else if (update == 1)
        {
            updateMsg.setText("Sign Up Successful! Please Sign In");
        }
        else if (update == 0)
        {
            updateMsg.setText("Account already exists. Please Sign In");
        }
    }

    public void onSignUpClick(View view) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, SignUpScreen.class);
            startActivity(intent);
        }, 0);
    }

    public void onSignInClick(View view) {
        userInput = findViewById(R.id.userInput);
        passInput = findViewById(R.id.passInput);

        String username = userInput.getText().toString();
        String password = passInput.getText().toString();


    }
}