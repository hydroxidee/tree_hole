package com.example.treehole;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class loginTest {

    private LoginManager loginManager;
    private DatabaseReference mockReference;

    @Before
    public void setUp() {
        // Mock DatabaseReference
        mockReference = mock(DatabaseReference.class);

        // Initialize LoginManager with mocked DatabaseReference
        loginManager = new LoginManager(mockReference);
    }

    @Test
    public void testValidCredentials() {
        String username = "validUser@usc.edu";
        String password = "validPassword";
        String shortUser = "validUser";

        // Mock the chain of DatabaseReference.child()
        DatabaseReference usersRef = mock(DatabaseReference.class);
        DatabaseReference userRef = mock(DatabaseReference.class);

        when(mockReference.child("users")).thenReturn(usersRef);
        when(usersRef.child(shortUser)).thenReturn(userRef);

        // Mock Firebase data retrieval
        doAnswer(invocation -> {
            ValueEventListener listener = invocation.getArgument(0);

            DataSnapshot dataSnapshot = mock(DataSnapshot.class);
            when(dataSnapshot.exists()).thenReturn(true);

            HashMap<String, Object> userData = new HashMap<>();
            userData.put("password", password);
            userData.put("first", "John");

            when(dataSnapshot.getValue()).thenReturn(userData);

            listener.onDataChange(dataSnapshot);
            return null;
        }).when(userRef).addListenerForSingleValueEvent(any(ValueEventListener.class));

        loginManager.login(username, password, new LoginManager.LoginCallback() {
            @Override
            public void onSuccess(String user, String firstName) {
                assertEquals(shortUser, user);
                assertEquals("John", firstName);
            }

            @Override
            public void onError(int errorType) {
                fail("Login should succeed with valid credentials");
            }
        });
    }

    @Test
    public void testInvalidEmailFormat() {
        String username = "invalidEmail";
        String password = "password";

        loginManager.login(username, password, new LoginManager.LoginCallback() {
            @Override
            public void onSuccess(String user, String firstName) {
                fail("Login should fail with invalid email format");
            }

            @Override
            public void onError(int errorType) {
                assertEquals(LoginManager.ERROR_INVALID_EMAIL, errorType);
            }
        });
    }

    @Test
    public void testUserDoesNotExist() {
        String username = "nonexistent@usc.edu";
        String password = "password";
        String shortUser = "nonexistent";

        // Mock the chain of DatabaseReference.child()
        DatabaseReference usersRef = mock(DatabaseReference.class);
        DatabaseReference userRef = mock(DatabaseReference.class);

        when(mockReference.child("users")).thenReturn(usersRef);
        when(usersRef.child(shortUser)).thenReturn(userRef);

        // Mock Firebase data retrieval
        doAnswer(invocation -> {
            ValueEventListener listener = invocation.getArgument(0);

            DataSnapshot dataSnapshot = mock(DataSnapshot.class);
            when(dataSnapshot.exists()).thenReturn(false);

            listener.onDataChange(dataSnapshot);
            return null;
        }).when(userRef).addListenerForSingleValueEvent(any(ValueEventListener.class));

        loginManager.login(username, password, new LoginManager.LoginCallback() {
            @Override
            public void onSuccess(String user, String firstName) {
                fail("Login should fail when user does not exist");
            }

            @Override
            public void onError(int errorType) {
                assertEquals(LoginManager.ERROR_USER_NOT_EXIST, errorType);
            }
        });
    }

    @Test
    public void testIncorrectPassword() {
        String username = "validUser@usc.edu";
        String password = "wrongPassword";
        String shortUser = "validUser";

        // Mock the chain of DatabaseReference.child()
        DatabaseReference usersRef = mock(DatabaseReference.class);
        DatabaseReference userRef = mock(DatabaseReference.class);

        when(mockReference.child("users")).thenReturn(usersRef);
        when(usersRef.child(shortUser)).thenReturn(userRef);

        // Mock Firebase data retrieval
        doAnswer(invocation -> {
            ValueEventListener listener = invocation.getArgument(0);

            DataSnapshot dataSnapshot = mock(DataSnapshot.class);
            when(dataSnapshot.exists()).thenReturn(true);

            HashMap<String, Object> userData = new HashMap<>();
            userData.put("password", "correctPassword");
            userData.put("first", "John");

            when(dataSnapshot.getValue()).thenReturn(userData);

            listener.onDataChange(dataSnapshot);
            return null;
        }).when(userRef).addListenerForSingleValueEvent(any(ValueEventListener.class));

        loginManager.login(username, password, new LoginManager.LoginCallback() {
            @Override
            public void onSuccess(String user, String firstName) {
                fail("Login should fail with incorrect password");
            }

            @Override
            public void onError(int errorType) {
                assertEquals(LoginManager.ERROR_INCORRECT_PASSWORD, errorType);
            }
        });
    }

    @Test
    public void testNullUsername() {
        String username = null;
        String password = "password";

        loginManager.login(username, password, new LoginManager.LoginCallback() {
            @Override
            public void onSuccess(String user, String firstName) {
                fail("Login should fail with null username");
            }

            @Override
            public void onError(int errorType) {
                assertEquals(LoginManager.ERROR_INVALID_EMAIL, errorType);
            }
        });
    }

    @Test
    public void testNullPassword() {
        String username = "validUser@usc.edu";
        String password = null;
        String shortUser = "validUser";

        // Mock the chain of DatabaseReference.child()
        DatabaseReference usersRef = mock(DatabaseReference.class);
        DatabaseReference userRef = mock(DatabaseReference.class);

        when(mockReference.child("users")).thenReturn(usersRef);
        when(usersRef.child(shortUser)).thenReturn(userRef);

        // Mock Firebase data retrieval
        doAnswer(invocation -> {
            ValueEventListener listener = invocation.getArgument(0);

            DataSnapshot dataSnapshot = mock(DataSnapshot.class);
            when(dataSnapshot.exists()).thenReturn(true);

            HashMap<String, Object> userData = new HashMap<>();
            userData.put("password", "correctPassword");
            userData.put("first", "John");

            when(dataSnapshot.getValue()).thenReturn(userData);

            listener.onDataChange(dataSnapshot);
            return null;
        }).when(userRef).addListenerForSingleValueEvent(any(ValueEventListener.class));

        loginManager.login(username, password, new LoginManager.LoginCallback() {
            @Override
            public void onSuccess(String user, String firstName) {
                fail("Login should fail with null password");
            }

            @Override
            public void onError(int errorType) {
                assertEquals(LoginManager.ERROR_INCORRECT_PASSWORD, errorType);
            }
        });
    }
}
