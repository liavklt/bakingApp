package com.example.android.bakingapp;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by lianavklt on 15/07/2018.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

  @Rule
  public IntentsTestRule<MainActivity> mainActivityActivityTestRule = new IntentsTestRule<>(
      MainActivity.class);


  @Test
  public void clickNameAndGoToNextActivity() {

    Espresso.onData(anything()).inAdapterView(withId(R.id.lv_recipes)).atPosition(1)
        .perform(click());
    intended(hasComponent(MasterListActivity.class.getName()));

  }
}