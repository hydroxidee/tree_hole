package com.example.treehole;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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

public class SignUpScreen extends AppCompatActivity {
    private FirebaseDatabase root;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.sign_up_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        root = FirebaseDatabase.getInstance("https://treehole-database-default-rtdb.firebaseio.com/");
        reference = root.getReference();

        // Sets up Spinner
        Spinner spinner = findViewById(R.id.roleTypes);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.role_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @SuppressLint("SetTextI18n")
    public void onSignUpClick(View view) {
        //retrieves all of the user information
        EditText firstInput = findViewById(R.id.firstNameInput);
        String first = firstInput.getText().toString();
        EditText lastInput = findViewById(R.id.lastNameInput);
        String last = lastInput.getText().toString();
        EditText userInput = findViewById(R.id.userInput);
        String user = userInput.getText().toString();
        EditText passInput = findViewById(R.id.passInput);
        String pass = passInput.getText().toString();
        EditText bioInput = findViewById(R.id.bioInput);
        String bio = bioInput.getText().toString();
        EditText numIDInput = findViewById(R.id.numIDInput);
        String numID = numIDInput.getText().toString();
        Spinner roleTypeInput = findViewById(R.id.roleTypes);
        String roleType = roleTypeInput.getSelectedItem().toString();

        TextView error = findViewById(R.id.errorMessage);

        // validates all the input information
        boolean valid = true;
        if(first.isEmpty() || first.length() > 20)
        {
            valid = false;
            error.setText("** First name must be empty or exceed 20 characters");
        }
        else if(last.isEmpty() || last.length() > 20)
        {
            valid = false;
            error.setText("** Last name must be empty or exceed 20 characters");
        }
        else if(user.isEmpty() || !user.matches("^[a-zA-Z0-9._%+-]+@usc\\.edu$"))
        {
            valid = false;
            error.setText("** Email must be a valid USC email");
        }
        else if(pass.isEmpty() || !pass.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"))
        {
            valid = false;
            error.setText("** Password must be a minimum of 8 letters and numbers");
        }
        else if(bio.length() > 300)
        {
            valid = false;
            error.setText("** Bio must not exceed 300 characters");
        }
        else if(numID.isEmpty() || !numID.matches("\\d{10}"))
        {
            valid = false;
            error.setText("** ID Number must be a valid ID Number");
        }


        //created new user if validation checks all pass
        if(valid)
        {
            error.setText("");

            // creates user info map
            String shortEmail = user.substring(0, user.indexOf("@"));
            HashMap<String, String> info = new HashMap<>();
            info.put("first", first);
            info.put("last", last);
            info.put("username", shortEmail);
            info.put("password", pass);
            info.put("bio", bio);
            info.put("ID", numID);
            info.put("role", roleType);

            DatabaseReference userRef = reference.child("users").child(shortEmail);
            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(!dataSnapshot.exists()) {
                        //create new user
                        makeNewUser(shortEmail, info);
                    }
                    else
                    {
                        userExists();
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

    // creates new user and navigates back to sign in screen to prompt sign in
    private void makeNewUser(String shortEmail, HashMap<String, String> info){
        reference.child("users").child(shortEmail).setValue(info);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(SignUpScreen.this, MainActivity.class);
            intent.putExtra("update", 1);
            startActivity(intent);
        }, 0);
    }

    // navigates back to sign in screen and notifies that the user already exists
    private void userExists()
    {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(SignUpScreen.this, MainActivity.class);
            intent.putExtra("update", 0);
            startActivity(intent);
        }, 0);
    }
}
