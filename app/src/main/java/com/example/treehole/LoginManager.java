package com.example.treehole;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class LoginManager {

    // Error codes
    public static final int ERROR_INVALID_EMAIL = -1;
    public static final int ERROR_USER_NOT_EXIST = 0;
    public static final int ERROR_INCORRECT_PASSWORD = 1;
    public static final int ERROR_SYSTEM_DOWN = -2;

    // Firebase references
    private final DatabaseReference reference;


    // Constructor with dependency injection
    public LoginManager(DatabaseReference reference) {
        this.reference = reference;
    }
// default constructor
    public LoginManager() {
        this.reference = FirebaseDatabase.getInstance("https://treehole-database-default-rtdb.firebaseio.com/").getReference();
    }

    // Login callback interface
    public interface LoginCallback {
        void onSuccess(String user, String firstName);

        void onError(int errorType);
    }

    // Login method
    public void login(String username, String password, LoginCallback callback) {
        // Validate email format
        if (!validateEmail(username)) {
            callback.onError(ERROR_INVALID_EMAIL); // Invalid email format
            return;
        }

        String shortUser = username.substring(0, username.indexOf("@"));
        DatabaseReference userRef = reference.child("users").child(shortUser);

        // Retrieve user data from Firebase
        userRef.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    HashMap<String, Object> info = (HashMap<String, Object>) dataSnapshot.getValue();
                    if (info == null) {
                        callback.onError(ERROR_SYSTEM_DOWN);
                        return;
                    }

                    String storedPassword = (String) info.get("password");
                    String firstName = (String) info.get("first");

                    // Check if the password matches
                    if (storedPassword != null && storedPassword.equals(password)) {
                        callback.onSuccess(shortUser, firstName);
                    } else {
                        callback.onError(ERROR_INCORRECT_PASSWORD); // Incorrect password
                    }
                } else {
                    callback.onError(ERROR_USER_NOT_EXIST); // User does not exist
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(ERROR_SYSTEM_DOWN); // Database error
            }
        });
    }

    // Validates that the email is a USC email and properly formatted
    public boolean validateEmail(String email) {
        return email != null && email.matches("^[a-zA-Z0-9._%+-]+@usc\\.edu$");
    }
}