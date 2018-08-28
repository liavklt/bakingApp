package com.example.android.bakingapp;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by lianavklt on 28/08/2018.
 */
@RunWith(AndroidJUnit4.class)

public class MasterListActivityUITest {

  @Rule
  public ActivityTestRule<MainActivity> masterListActivityActivityTestRule = new ActivityTestRule<>(
      MainActivity.class);

  @Test
  public void clickOnRecipeStepAndGoToStepDetails() {
    onData(anything()).inAdapterView(withId(R.id.lv_recipes)).atPosition(1)
        .perform(click());
    onView(ViewMatchers.withId(R.id.steps_recycler_view))
        .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    onView(ViewMatchers.withId(R.id.step_textview)).check(matches(isDisplayed()));
  }
}