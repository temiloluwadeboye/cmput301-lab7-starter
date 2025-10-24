package com.example.androiduitesting;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new
            ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void testAddCity(){
        //click the add button
        onView(withId(R.id.button_add)).perform(click());
        //type Edmonton in the edit text
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton"));
        //press confirm
        onView(withId(R.id.button_confirm)).perform(click());
        //check its in the list
        onView(withText("Edmonton")).check(matches(isDisplayed()));
    }

    @Test
    public void testClearCity(){
        // Add first city to the list
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());
        //Add another city to the list
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Vancouver"));
        onView(withId(R.id.button_confirm)).perform(click());
        //Clear the list
        onView(withId(R.id.button_clear)).perform(click());
        onView(withText("Edmonton")).check(doesNotExist());
        onView(withText("Vancouver")).check(doesNotExist());
    }

    @Test
    public void testListView(){
        // Add a city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());
        // Check if in the Adapter view (given id of that adapter view),there is a data
        // (which is an instance of String) located at position zero.
        // If this data matches the text we provided then Voila! Our test should pass
        // You can also use anything() in place of is(instanceOf(String.class))
        onData(is(instanceOf(String.class))).inAdapterView(withId(R.id.city_list)).atPosition(0).check(matches((withText("Edmonton"))));
    }

    @Test
    public void testActivitySwitched(){
        // Add a city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        //clicking on the item on list
        onView(withText("Edmonton")).perform(click());
        //checking if it is displayed in the new activity
        onView(withText("Edmonton")).check(matches(isDisplayed()));
    }

    @Test
    public void testCityNameConsistent(){
        //Adding an city to the list
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        //Adding another city to the list
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Vancouver"));
        onView(withId(R.id.button_confirm)).perform(click());

        //clicking on one of the cities
        onView(withText("Edmonton")).perform(click());
        //checking if the city displayed in new activity is consistent with the item clicked
        onView(withText("Edmonton")).check(matches(isDisplayed()));
    }

    @Test
    public void testBackButton(){
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        //Adding another city to the list
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Vancouver"));
        onView(withId(R.id.button_confirm)).perform(click());

        //clicking on item in the list
        onView(withText("Edmonton")).perform(click());
        //clicking on the back button in new activity
        onView(withId(R.id.back_button)).perform(click());
        //checking if it returned by checking if the city not clicked is displayed in the activity
        onView(withText("Edmonton")).check(matches(isDisplayed()));

    }

}