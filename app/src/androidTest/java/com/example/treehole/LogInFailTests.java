package com.example.treehole;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.google.firebase.database.FirebaseDatabase;

import org.junit.Rule;
import org.junit.Test;

public class LogInFailTests {
    public static final String EMAIL_SUCCESS = "ryoh@usc.edu";
    public static final String PASS_SUCCESS = "Pass1234";

    public static final String EMAIL_NO_EXIST = "pretend@usc.edu";
    public static final String PASS_FAIL = "Password";

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testUserDoesNotExist()
    {
        // Type text and then press the button.
        onView(withId(R.id.userInput))
                .perform(typeText(EMAIL_NO_EXIST), closeSoftKeyboard());
        onView(withId(R.id.passInput))
                .perform(typeText(PASS_SUCCESS), closeSoftKeyboard());
        onView(withId(R.id.toSignIn)).perform(click());

        // Check that the text was changed.
        onView(withId(R.id.updateMessage)).check(matches(withText("** User does not exist, please sign up")));
    }

    @Test
    public void testIncorrectPassword()
    {
        // Type text and then press the button.
        onView(withId(R.id.userInput))
                .perform(typeText(EMAIL_SUCCESS), closeSoftKeyboard());
        onView(withId(R.id.passInput))
                .perform(typeText(PASS_FAIL), closeSoftKeyboard());
        onView(withId(R.id.toSignIn)).perform(click());

        // Check that the text was changed.
        onView(withId(R.id.updateMessage)).check(matches(withText("** Incorrect Password")));
    }

    @Test
    public void testFirebaseFail()
    {
        FirebaseDatabase.getInstance("https://treehole-database-default-rtdb.firebaseio.com/").goOffline();

        // Type text and then press the button.
        onView(withId(R.id.userInput))
                .perform(typeText(EMAIL_SUCCESS), closeSoftKeyboard());
        onView(withId(R.id.passInput))
                .perform(typeText(PASS_FAIL), closeSoftKeyboard());
        onView(withId(R.id.toSignIn)).perform(click());

        // Check that the text was changed.
        onView(withId(R.id.updateMessage)).check(matches(withText("** System Down, please wait and try again")));

        FirebaseDatabase.getInstance("https://treehole-database-default-rtdb.firebaseio.com/").goOnline();
    }
}

