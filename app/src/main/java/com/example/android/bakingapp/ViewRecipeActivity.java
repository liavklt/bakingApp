package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

/**
 * Created by lianavklt on 25/06/2018.
 */

public class ViewRecipeActivity extends AppCompatActivity {

  private Recipe recipe;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_view_recipe);
    recipe = getIntent().getParcelableExtra("recipeIntent");
    if (savedInstanceState == null) {
      //instantiate new Fragments for Video and step instructions

      android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
      ViewRecipeFragment stepFragment = new ViewRecipeFragment();
      Step step = getIntent().getParcelableExtra("step");
      stepFragment.setDescription(step.getDescription());
      VideoFragment videoFragment = new VideoFragment();
      videoFragment.setVideoUrl(step.getVideoUrl());

      fragmentManager.beginTransaction().add(R.id.video_container, videoFragment).commit();
      fragmentManager.beginTransaction().add(R.id.step_instructions_container, stepFragment)
          .commit();
    }
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    boolean fromNewActivity = true;

    Intent masterListIntent = new Intent(this, MasterListActivity.class);
    Bundle bundleObj = new Bundle();
    bundleObj.putString("fromNewActivity", Boolean.toString(fromNewActivity));
    bundleObj.putParcelable("recipe", recipe);
    masterListIntent.putExtras(bundleObj);
    startActivityForResult(masterListIntent, 0);
  }
}
