package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

/**
 * Created by lianavklt on 25/06/2018.
 */

public class ViewRecipeActivity extends AppCompatActivity {

  public static final String EXTRA_POSITION = "extra_position";
  public static final int DEFAULT_POSITION = -1;
  Recipe recipe;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_view_recipe);
//    int stepPosition = getIntent().getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
//    if (stepPosition == DEFAULT_POSITION) {
//      // EXTRA_POSITION not found in intent
//      finish();
//      Toast.makeText(this, "Recipe data not available", Toast.LENGTH_SHORT).show();
//    }
    Intent intent = getIntent();
    recipe = intent.getParcelableExtra("recipeIntent");

    if (savedInstanceState == null) {
      //instantiate new Fragments for Video and step instructions

      android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
      ViewRecipeFragment stepFragment = new ViewRecipeFragment();
      Step step = getIntent().getParcelableExtra("step");
      stepFragment.setDescription(step != null ? step.getDescription() : "");
      VideoFragment videoFragment = new VideoFragment();
      videoFragment.setVideoUrl(step != null ? step.getVideoUrl() : "");
      videoFragment.setStepTextView((FrameLayout) findViewById(R.id.step_instructions_container));


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

//    Intent masterListIntent = new Intent(this, MasterListActivity.class);
//    Bundle bundleObj = new Bundle();
//    bundleObj.putString("fromNewActivity", Boolean.toString(true));
//    bundleObj.putParcelable("recipe",recipe);
//    masterListIntent.putExtras(bundleObj);
//    startActivity(masterListIntent);
  }
}
