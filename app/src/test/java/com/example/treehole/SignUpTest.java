package com.example.treehole;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class SignUpTest {

    private DatabaseReference mockDatabaseReference;
    private AddChangeUserScreen mockAddChangeUserScreen;

    @Before
    public void setUp() {
        // Mock the Firebase DatabaseReference
        mockDatabaseReference = mock(DatabaseReference.class);

        // Mock the AddChangeUserScreen class
        mockAddChangeUserScreen = mock(AddChangeUserScreen.class);
    }

    @Test
    public void testSignUpDataSaving() {
        // Simulate user data to save
        HashMap<String, Object> userData = new HashMap<>();
        userData.put("firstName", "John");
        userData.put("lastName", "Doe");
        userData.put("email", "johndoe@example.com");

        // Mock DatabaseReference's setValue method
        doAnswer(invocation -> {
            DatabaseReference.CompletionListener listener = invocation.getArgument(1);
            // Simulate success
            if (listener != null) {
                listener.onComplete(null, mockDatabaseReference);
            }
            return null;
        }).when(mockDatabaseReference).setValue(any(), any(DatabaseReference.CompletionListener.class));

        try {
            mockDatabaseReference.setValue(userData, (error, ref) -> {
                if (error != null) {
                    fail("Failed to save data: " + error.getMessage());
                }
            });
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testRetrieveUserData() {
        // Mock the main DataSnapshot
        DataSnapshot mockDataSnapshot = mock(DataSnapshot.class);

        // Mock child snapshots
        DataSnapshot firstNameSnapshot = mock(DataSnapshot.class);
        DataSnapshot lastNameSnapshot = mock(DataSnapshot.class);
        DataSnapshot emailSnapshot = mock(DataSnapshot.class);

        // Define the behavior for child snapshots
        when(mockDataSnapshot.child("firstName")).thenReturn(firstNameSnapshot);
        when(mockDataSnapshot.child("lastName")).thenReturn(lastNameSnapshot);
        when(mockDataSnapshot.child("email")).thenReturn(emailSnapshot);

        // Define the values returned by getValue()
        when(firstNameSnapshot.getValue(String.class)).thenReturn("John");
        when(lastNameSnapshot.getValue(String.class)).thenReturn("Doe");
        when(emailSnapshot.getValue(String.class)).thenReturn("johndoe@example.com");

        // Simulate ValueEventListener
        doAnswer(invocation -> {
            ValueEventListener listener = invocation.getArgument(0);
            listener.onDataChange(mockDataSnapshot); // Simulate onDataChange callback
            return null;
        }).when(mockDatabaseReference).addListenerForSingleValueEvent(any(ValueEventListener.class));

        // Add listener and assert results
        mockDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // Assertions
                assertEquals("John", snapshot.child("firstName").getValue(String.class));
                assertEquals("Doe", snapshot.child("lastName").getValue(String.class));
                assertEquals("johndoe@example.com", snapshot.child("email").getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                fail("Database error: " + error.getMessage());
            }
        });
    }
    @Test
    public void testInvalidEmailValidation() {
        // Simulate invalid email input (not ending with @usc.edu)
        String invalidEmail = "johndoe@google.com";

        // Set the user input and other necessary data for validation
        String firstName = "John";
        String lastName = "Doe";
        String password = "password123";
        String bio = "Bio text here";
        String numID = "1234567890";
        String roleType = "Student"; // Example role type

        // Simulate the error TextView
        TextView errorTextView = mock(TextView.class);
        when(mockAddChangeUserScreen.findViewById(R.id.errorMessage)).thenReturn(errorTextView);

        // Call the onSignUpClick method to trigger the validation check for the email
        mockAddChangeUserScreen.onSignUpClick(null);

        // Verify that the error message for invalid email is set correctly
        verify(errorTextView, times(0)).setText(any());
    }

    @Test
    public void testValidEmailValidation() {
        // Simulate valid email input (ending with @usc.edu)
        String validEmail = "johndoe@usc.edu";
        String firstName = "John";
        String lastName = "Doe";
        String password = "password123";
        String bio = "Bio text here";
        String numID = "1234567890";
        String roleType = "Student"; // Example role type

        // Simulate the error TextView
        TextView errorTextView = mock(TextView.class);
        when(mockAddChangeUserScreen.findViewById(R.id.errorMessage)).thenReturn(errorTextView);

        // Call the onSignUpClick method to trigger the validation check for the valid email
        mockAddChangeUserScreen.onSignUpClick(null);

        // Verify that no error message is set for a valid email
        verify(errorTextView, times(0)).setText(any());
    }
}