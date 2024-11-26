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

public class MainActivity extends AppCompatActivity {

    // User input fields
    private EditText userInput;
    private EditText passInput;
    private TextView updateMsg;

    // Login manager instance
    private LoginManager loginManager;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Adjusting for system bars (optional, based on your layout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize LoginManager
        loginManager = new LoginManager();
        // Initialize UI elements
        updateMsg = findViewById(R.id.updateMessage);
        userInput = findViewById(R.id.userInput);
        passInput = findViewById(R.id.passInput);

        // Check for any updates from Sign Up or Sign Out actions
        int update = getIntent().getIntExtra("update", -1);
        if (update == -1) {
            updateMsg.setText("");
        } else if (update == 1) {
            updateMsg.setText("Sign Up Successful! Please Sign In");
        } else if (update == 0) {
            updateMsg.setText("Account already exists. Please Sign In");
        } else if (update == 2) {
            updateMsg.setText("Successfully Signed Out");
        }
    }

    // Navigates to the Sign Up page
    public void onSignUpClick(View view) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, AddChangeUserScreen.class);
            // Makes AddChangeUserScreen a Sign Up screen
            intent.putExtra("type", 0);
            startActivity(intent);
        }, 0);
    }

    // Handles Sign In button click
    public void onSignInClick(View view) {
        // Retrieves sign-in information
        String username = userInput.getText().toString().trim();
        String password = passInput.getText().toString().trim();

        // Calls the login method from LoginManager
        loginManager.login(username, password, new LoginManager.LoginCallback() {
            @Override
            public void onSuccess(String user, String firstName) {
                updateMsg.setText("Successfully Signed In");
                UserInfo.SetUser(user);
                UserInfo.setFirstName(firstName);
                navigateToHomepage();
            }

            @Override
            public void onError(int errorType) {
                switch (errorType) {
                    case LoginManager.ERROR_INVALID_EMAIL:
                        updateMsg.setText("** Invalid Email");
                        break;
                    case LoginManager.ERROR_USER_NOT_EXIST:
                        updateMsg.setText("** User does not exist, please sign up");
                        break;
                    case LoginManager.ERROR_INCORRECT_PASSWORD:
                        updateMsg.setText("** Incorrect Password");
                        break;
                    default:
                        updateMsg.setText("** System Down, please try again later");
                        break;
                }
            }
        });
    }

    // Navigates to the Homepage after successful login
    private void navigateToHomepage() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, Homepage.class);
            startActivity(intent);
        }, 500);
    }
}
