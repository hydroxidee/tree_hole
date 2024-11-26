package com.example.treehole;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import static androidx.test.espresso.Espresso.onData;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.Matchers.*;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.junit.Rule;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AcademicPostTest {
    public static final String EMAIL_SUCCESS = "ryoh@usc.edu";
    public static final String PASS_SUCCESS = "Pass1234";

    public static String databaseOutput = "";

    @Rule
    public ActivityScenarioRule<PostAcademic> activityScenarioRule
            = new ActivityScenarioRule<>(PostAcademic.class);

    @Test
    public void testAcademicPost() throws InterruptedException {
        //set user

        onView(withId(R.id.title))
                .perform(typeText("Academic User Test"), closeSoftKeyboard());
        onView(withId(R.id.content))
                .perform(typeText("test test test test test"), closeSoftKeyboard());
        onView(withId(R.id.name)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Anonymous"))).perform(click());

        onView(withId(R.id.post_button)).perform(click());

        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        Thread.sleep(3000);

        DatabaseReference reference = FirebaseDatabase.getInstance("https://treehole-database-default-rtdb.firebaseio.com/").getReference("posts").child("academic").child(timestamp);

        Query query = reference.orderByChild("username").equalTo("Anonymous");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // The value exists
                    databaseOutput = "true";
                    assertEquals("true", databaseOutput);
                } else {
                    // The value does not exist
                    databaseOutput = "false";
                    assertEquals("true", databaseOutput);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("Error checking database: " + databaseError.getMessage());
            }
        });
    }
}
