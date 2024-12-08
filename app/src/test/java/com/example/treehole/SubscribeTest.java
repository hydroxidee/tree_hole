package com.example.treehole;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;

public class SubscribeTest {

    private DatabaseReference mockDatabaseReference;
    private MockedStatic<FirebaseDatabase> mockedFirebaseDatabase;

    @Before
    public void setUp() {
        // Mock FirebaseDatabase.getInstance(String url)
        mockedFirebaseDatabase = mockStatic(FirebaseDatabase.class);
        FirebaseDatabase mockFirebaseDatabase = mock(FirebaseDatabase.class);
        mockedFirebaseDatabase.when(() -> FirebaseDatabase.getInstance(anyString())).thenReturn(mockFirebaseDatabase);

        // Mock DatabaseReference
        mockDatabaseReference = mock(DatabaseReference.class);
        when(mockFirebaseDatabase.getReference()).thenReturn(mockDatabaseReference);

        // Mock the chain of child() calls
        DatabaseReference userRef = mock(DatabaseReference.class);
        DatabaseReference testUserRef = mock(DatabaseReference.class);
        DatabaseReference notifsRef = mock(DatabaseReference.class);

        when(mockDatabaseReference.child("users")).thenReturn(userRef);
        when(userRef.child("testuser")).thenReturn(testUserRef);
        when(testUserRef.child("notifs")).thenReturn(notifsRef);

        // Mock updateChildren on notifsRef
        Task<Void> mockTask = Tasks.forResult(null); // or mock(Task.class)
        when(notifsRef.updateChildren(anyMap())).thenReturn(mockTask);

        // Set mock reference in UserInfo
        UserInfo.setDatabaseReference(mockDatabaseReference);

        // Simulate UserInfo setup
        UserInfo.SetUser("testuser");
        UserInfo.setFirstName("Test");

        // Simulate following the communities
        UserInfo.followAcademic();
        UserInfo.followLife();
        UserInfo.followEvent();
    }

    @After
    public void tearDown() {
        // Close the static mock
        mockedFirebaseDatabase.close();
    }

    @Test
    public void testSubscribedCategoriesDisplayed() {
        // Simulate Button behavior using plain Java objects instead of Android buttons
        TestButton academicButton = new TestButton();
        academicButton.setEnabled(UserInfo.isFollowingAcademic());
        academicButton.setText("AcademicButton0");

        TestButton lifeButton = new TestButton();
        lifeButton.setEnabled(UserInfo.isFollowingLife());
        lifeButton.setText("LifeButton0");

        TestButton eventButton = new TestButton();
        eventButton.setEnabled(UserInfo.isFollowingEvent());
        eventButton.setText("EventButton0");

        // Verify Academic Button
        assertNotNull(academicButton);
        assertTrue(academicButton.isEnabled());
        assertEquals("AcademicButton0", academicButton.getText());

        // Verify Life Button
        assertNotNull(lifeButton);
        assertTrue(lifeButton.isEnabled());
        assertEquals("LifeButton0", lifeButton.getText());

        // Verify Event Button
        assertNotNull(eventButton);
        assertTrue(eventButton.isEnabled());
        assertEquals("EventButton0", eventButton.getText());
    }

    // Helper class to simulate a button without Android dependencies
    private static class TestButton {
        private boolean enabled;
        private String text;

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}