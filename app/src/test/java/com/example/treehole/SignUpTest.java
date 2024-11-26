package com.example.treehole;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class SignUpTest {

    private DatabaseReference mockDatabaseReference;

    @Before
    public void setUp() {
        // Mock the Firebase DatabaseReference
        mockDatabaseReference = mock(DatabaseReference.class);
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
}