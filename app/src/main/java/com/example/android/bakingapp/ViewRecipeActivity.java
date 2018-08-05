package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

/**
 * Created by lianavklt on 25/06/2018.
 */

public class ViewRecipeActivity extends AppCompatActivity implements OnClickListener {

  Recipe recipe;
  int stepPosition;
  @BindView(R.id.previousButton)
  Button previousButton;
  @BindView(R.id.nextButton)
  Button nextButton;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_view_recipe);
    ButterKnife.bind(this);
    previousButton.setOnClickListener(this);

    nextButton.setOnClickListener(this);
    Intent intent = getIntent();
    recipe = intent.getParcelableExtra("recipeIntent");
    stepPosition = intent.getIntExtra("position", 0);

    setButtonsVisibility();

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

  private void setButtonsVisibility() {
    if (stepPosition == 0) {
      previousButton.setVisibility(View.INVISIBLE);

    } else if (stepPosition == recipe.getSteps().size() - 1) {
      nextButton.setVisibility(View.INVISIBLE);
    } else {
      previousButton.setVisibility(View.VISIBLE);
      nextButton.setVisibility(View.VISIBLE);
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
  }

  @Override
  public void onClick(View v) {
    int id = v.getId();
    if (R.id.nextButton == id) {
      Step nextStep = recipe.getSteps().get(stepPosition + 1);
      stepPosition++;
      handleFragmentsReplacement(nextStep);
    } else if (R.id.previousButton == id) {
      Step previousStep = recipe.getSteps().get(stepPosition - 1);
      stepPosition--;
      handleFragmentsReplacement(previousStep);
    }

  }

  private void handleFragmentsReplacement(Step step) {
    setButtonsVisibility();
    ViewRecipeFragment viewRecipeFragment = new ViewRecipeFragment();
    viewRecipeFragment.setDescription(step.getDescription());

    VideoFragment videoFragment = new VideoFragment();
    videoFragment.setVideoUrl(step.getVideoUrl());
    videoFragment.setStepTextView((FrameLayout) findViewById(R.id.step_instructions_container));
    getSupportFragmentManager().beginTransaction().replace(R.id.video_container, videoFragment)
        .commit();
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.step_instructions_container, viewRecipeFragment).commit();
  }

}
