package com.example.treehole;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

public class SignUpErrorTest {
    public static final String longName = "Alexander Jonathan Christopher Montgomery the Third Esq.";
    public static final String longBio = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse dhdjfjkhdhjhjfdgkjhfjkhfghjgfhjkfghjkfgdjhkfgdhjkfgdhjkfghjkfdgjhkfdgjhkfdghjkfdghkjfdghjkfdghjfdghjkfgdhfdhgjkfhdgjk.\n";
    @Rule
    public ActivityScenarioRule<AddChangeUserScreen> activityScenarioRule
            = new ActivityScenarioRule<>(AddChangeUserScreen.class);

    @Test
    public void firstNameLongTest()
    {
        onView(withId(R.id.firstNameInput))
                .perform(typeText(longName), closeSoftKeyboard());
        onView(withId(R.id.signUpButton)).perform(click());

        onView(withId(R.id.errorMessage)).check(matches(withText("** First name must be empty or exceed 20 characters")));
    }

    @Test
    public void lastNameLongTest()
    {
        onView(withId(R.id.firstNameInput))
                .perform(typeText("Tim"), closeSoftKeyboard());
        onView(withId(R.id.lastNameInput))
                .perform(typeText(longName), closeSoftKeyboard());
        onView(withId(R.id.signUpButton)).perform(click());

        onView(withId(R.id.errorMessage)).check(matches(withText("** Last name must be empty or exceed 20 characters")));
    }

    @Test
    public void invalidEmail()
    {
        onView(withId(R.id.firstNameInput))
                .perform(typeText("Tim"), closeSoftKeyboard());
        onView(withId(R.id.lastNameInput))
                .perform(typeText("Tim"), closeSoftKeyboard());
        onView(withId(R.id.userInput))
                .perform(typeText("test@gmail.edu"), closeSoftKeyboard());
        onView(withId(R.id.signUpButton)).perform(click());

        onView(withId(R.id.errorMessage)).check(matches(withText("** Email must be a valid USC email")));
    }


    @Test
    public void bioLongTest()
    {
        onView(withId(R.id.firstNameInput))
                .perform(typeText("Tim"), closeSoftKeyboard());
        onView(withId(R.id.lastNameInput))
                .perform(typeText("Tim"), closeSoftKeyboard());
        onView(withId(R.id.userInput))
                .perform(typeText("test@usc.edu"), closeSoftKeyboard());
        onView(withId(R.id.passInput))
                .perform(typeText("Pass1234"), closeSoftKeyboard());
        onView(withId(R.id.numIDInput))
                .perform(typeText("12345678890"), closeSoftKeyboard());
        onView(withId(R.id.bioInput))
                .perform(typeText(longBio), closeSoftKeyboard());
        onView(withId(R.id.signUpButton)).perform(click());

        onView(withId(R.id.errorMessage)).check(matches(withText("** Bio must not exceed 300 characters")));
    }

    @Test
    public void invalidIDTest()
    {
        onView(withId(R.id.firstNameInput))
                .perform(typeText("Tim"), closeSoftKeyboard());
        onView(withId(R.id.lastNameInput))
                .perform(typeText("Tim"), closeSoftKeyboard());
        onView(withId(R.id.userInput))
                .perform(typeText("test@usc.edu"), closeSoftKeyboard());
        onView(withId(R.id.passInput))
                .perform(typeText("Pass1234"), closeSoftKeyboard());
        onView(withId(R.id.numIDInput))
                .perform(typeText("1"), closeSoftKeyboard());
        onView(withId(R.id.signUpButton)).perform(click());

        onView(withId(R.id.errorMessage)).check(matches(withText("** ID Number must be a valid ID Number")));
    }

    @Test
    public void invalidPassTest()
    {
        onView(withId(R.id.firstNameInput))
                .perform(typeText("Tim"), closeSoftKeyboard());
        onView(withId(R.id.lastNameInput))
                .perform(typeText("Tim"), closeSoftKeyboard());
        onView(withId(R.id.userInput))
                .perform(typeText("test@usc.edu"), closeSoftKeyboard());
        onView(withId(R.id.passInput))
                .perform(typeText("Passaaa"), closeSoftKeyboard());
        onView(withId(R.id.signUpButton)).perform(click());

        onView(withId(R.id.errorMessage)).check(matches(withText("** Password must be a minimum of 8 letters and numbers")));
    }


}
