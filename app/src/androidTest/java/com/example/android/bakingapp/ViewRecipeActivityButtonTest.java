package com.example.android.bakingapp;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by lianavklt on 28/08/2018.
 */
@RunWith(AndroidJUnit4.class)
public class ViewRecipeActivityButtonTest {

  private static final String NEXT_BUTTON = "NEXT";
  private static final String PREVIOUS_BUTTON = "PREVIOUS";
  @Rule
  public ActivityTestRule<ViewRecipeActivity> viewRecipeActivityIntentsTestRule = new ActivityTestRule<>(
      ViewRecipeActivity.class);

  @Test
  public void buttonHasCorrectText() {

    onView(withId(R.id.nextButton)).check(matches(withText(NEXT_BUTTON)));
    onView(withId(R.id.previousButton)).check(matches(withText(PREVIOUS_BUTTON)));

  }


}