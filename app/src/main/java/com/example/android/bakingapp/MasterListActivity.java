package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

// This activity is responsible for displaying the master list of all recipes
// Implement the MasterListFragment callback, OnTextClickListener
public class MasterListActivity extends AppCompatActivity implements
    MasterListFragment.OnTextClickListener {

  private boolean mTwoPane;
  private Recipe recipe;
  private int stepPosition;
  public static final String EXTRA_POSITION = "extra_position";


  @BindView(R.id.images_grid_view)
  GridView gridView;

//  @Override
//  protected void onRestoreInstanceState(Bundle savedInstanceState) {
//    super.onRestoreInstanceState(savedInstanceState);
//    if(savedInstanceState!=null){
//      stepPosition = savedInstanceState.getInt("stepPosition");
//      recipe = savedInstanceState.getParcelable("recipe");
//    }
//  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_select_recipe);
    this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    Bundle extras = getIntent().getExtras();
    boolean fromNewActivity =Boolean.parseBoolean( extras.getString("fromNewActivity"));
    if(fromNewActivity){
      recipe = extras.getParcelable("recipe");
    }
//    if (savedInstanceState != null) {
////      mContent = getSupportFragmentManager().getFragment(savedInstanceState, "fragment");
//      stepPosition = savedInstanceState.getInt("stepPosition");
//      recipe = savedInstanceState.getParcelable("recipe");
//    }

    Intent intent = getIntent();
    if (intent == null) {

      finish();
      Toast.makeText(this, "Recipe data not available", Toast.LENGTH_SHORT).show();
    }
    ButterKnife.bind(this);

    recipe = intent.getExtras().getParcelable("recipe");

    if (findViewById(R.id.view_recipe_linear_layout) != null) {
      mTwoPane = true;

      // Change the GridView to space out the images more on tablet
      ButterKnife.bind(this);
      gridView.setNumColumns(1);

      if (savedInstanceState == null) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        ViewRecipeFragment viewRecipeFragment = new ViewRecipeFragment();
        Step step = ((Recipe) intent.getParcelableExtra("recipe")).getSteps().get(0);
        viewRecipeFragment.setDescription(
            step.getDescription());
        VideoFragment videoFragment = new VideoFragment();
        videoFragment.setVideoUrl(step.getVideoUrl());

        fragmentManager.beginTransaction().add(R.id.video_container,videoFragment).commit();
        fragmentManager.beginTransaction().add(R.id.step_instructions_container, viewRecipeFragment)
            .commit();

      }
    } else {
      // We're in single-pane mode and displaying fragments on a phone in separate activities
      mTwoPane = false;

    }


  }


  public void onTextSelected(int position) {
    Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_SHORT).show();

    stepPosition = position;
    if (mTwoPane) {

    } else {


      Bundle bundle = new Bundle();
      bundle.putParcelable("step", recipe.getSteps().get(position));
      bundle.putInt("position",stepPosition);
      bundle.putParcelable("recipeIntent",recipe);

      final Intent newIntent = new Intent(this, ViewRecipeActivity.class);
      newIntent.putExtras(bundle);
      startActivity(newIntent);

    }

  }

//  @Override
//  protected void onSaveInstanceState(Bundle outState) {
//    super.onSaveInstanceState(outState);
//    outState.putParcelable("recipe",recipe);
//    outState.putInt("stepPosition",stepPosition);
//  }
}
