package com.example.treehole;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    // database information
    private FirebaseDatabase root;
    private DatabaseReference reference;

    // user's information
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

        // checks if sign up page was visited, and the results of that.
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

    // navigates to sign up page
    public void onSignUpClick(View view) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, AddChangeUserScreen.class);
            //makes Add Change User Screen a Sign Up screen
            intent.putExtra("type", 0);
            startActivity(intent);
        }, 0);
    }

    // signs the user in
    public void onSignInClick(View view) {
        //retrieves sign in information
        userInput = findViewById(R.id.userInput);
        passInput = findViewById(R.id.passInput);

        String username = userInput.getText().toString();
        String password = passInput.getText().toString();

        if(validateEmail(username)) {
            String shortUser = username.substring(0, username.indexOf("@"));
            DatabaseReference userRef = reference.child("users").child(shortUser);

            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        HashMap<String, String> info = (HashMap<String, String>) dataSnapshot.getValue();
                        TextView updateMsg = findViewById(R.id.updateMessage);
                        // checks if password is correct
                        if(Objects.equals(info != null ? info.get("password") : null, password))
                        {
                            SignIn(shortUser);
                        }
                        else
                        {
                            SignInError(1);
                        }
                    } else {
                        SignInError(0);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.w(TAG, "loadPost:onCancelled", error.toException());
                }
            };
            userRef.addListenerForSingleValueEvent(eventListener);
        }
    }

    //ensures that inputted email is formatted correctly and a USC email
    @SuppressLint("SetTextI18n")
    private boolean validateEmail(String email) {
        boolean validUser = true;
        TextView updateMsg = findViewById(R.id.updateMessage);

        if(email.isEmpty() || !email.matches("^[a-zA-Z0-9._%+-]+@usc\\.edu$"))
        {
            validUser = false;
            updateMsg.setText("** Invalid Email");
        }
        else
        {
            updateMsg.setText("");
        }

        return validUser;
    }

    // allows user to sign in
    private void SignIn(String user) {
        TextView updateMsg = findViewById(R.id.updateMessage);

        UserInfo.SetUser(user);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, Homepage.class);
            startActivity(intent);
        }, 0);
    }

    // prints different sign in errors
    @SuppressLint("SetTextI18n")
    private void SignInError(int type) {
        TextView updateMsg = findViewById(R.id.updateMessage);
        if(type == 0)
            updateMsg.setText("** User does not exist, please sign up");
        else if(type == 1)
            updateMsg.setText("** Incorrect Password");
    }
}