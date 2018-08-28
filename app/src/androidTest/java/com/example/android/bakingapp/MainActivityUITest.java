package com.example.android.bakingapp;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by lianavklt on 15/07/2018.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityUITest {


  @Rule
  public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(
      MainActivity.class);


  @Test
  public void clickOnRecyclerViewItemOpenMasterListActivity() {

    onData(anything()).inAdapterView(withId(R.id.lv_recipes)).atPosition(1)
        .perform(click());
    onView(withId(R.id.tv_ingredient_item)).check(matches(isDisplayed()));


  }


}