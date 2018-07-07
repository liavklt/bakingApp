package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import java.util.List;
import org.json.JSONException;
import utils.JsonUtils;

// This activity is responsible for displaying the master list of all recipes
// Implement the MasterListFragment callback, OnTextClickListener
public class MasterListActivity extends AppCompatActivity {

  public static final String EXTRA_POSITION = "extra_position";
  public static final int DEFAULT_POSITION = -1;
  public static final String EXTRA_RECIPE_ID = "com.example.android.bakingapp.extra.RECIPE_ID";
  List<Recipe> allRecipes;
  @BindView(R.id.steps_recycler_view)
  RecyclerView recyclerView;
  LinearLayoutManager linearLayoutManager;
  private boolean mTwoPane;
  private int stepPosition;
  private int recipePosition;
  private Step step;

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
    ButterKnife.bind(this);

    Intent intent = getIntent();
    if (intent == null) {

      finish();
      Toast.makeText(this, "Recipe data not available", Toast.LENGTH_SHORT).show();
    }
    recipePosition = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
    if (recipePosition == DEFAULT_POSITION) {
      // EXTRA_POSITION not found in intent
      finish();
      Toast.makeText(this, "Recipe data not available", Toast.LENGTH_SHORT).show();
    }
//    recyclerView.setHasFixedSize(true);
//    linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
//    recyclerView.setLayoutManager(linearLayoutManager);
//    recyclerView.setAdapter();
    MasterListFragment masterListFragment = new MasterListFragment();
    masterListFragment.initializeRecyclerView(recyclerView, recipePosition);
    if (findViewById(R.id.view_recipe_linear_layout) != null) {
      mTwoPane = true;

      if (savedInstanceState == null) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        ViewRecipeFragment viewRecipeFragment = new ViewRecipeFragment();
        try {
          step = JsonUtils.populateRecipeStepsFromJson(recipePosition).get(0);
        } catch (JSONException e) {
          e.printStackTrace();
        }
        viewRecipeFragment.setDescription(
            step.getDescription());
        VideoFragment videoFragment = new VideoFragment();
        videoFragment.setVideoUrl(step.getVideoUrl());

        fragmentManager.beginTransaction().add(R.id.video_container, videoFragment).commit();

        fragmentManager.beginTransaction().add(R.id.step_instructions_container, viewRecipeFragment)
            .commit();

      }
    } else {
      // We're in single-pane mode and displaying fragments on a phone in separate activities
      mTwoPane = false;

    }


  }

//  public void onTextSelected(int position) throws JSONException {
//    Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_SHORT).show();
//
//    stepPosition = position;
//    if (mTwoPane) {
//      step = JsonUtils.populateRecipeStepsFromJson(recipePosition).get(stepPosition);
//      ViewRecipeFragment viewRecipeFragment = new ViewRecipeFragment();
//      viewRecipeFragment.setDescription(step.getDescription());
//
//      VideoFragment videoFragment = new VideoFragment();
//      videoFragment.setVideoUrl(step.getVideoUrl());
//
//      getSupportFragmentManager().beginTransaction().replace(R.id.video_container, videoFragment)
//          .commit();
//      getSupportFragmentManager().beginTransaction()
//          .replace(R.id.step_instructions_container, viewRecipeFragment).commit();
//
//    } else {
//
//      Bundle bundle = new Bundle();
//      bundle.putParcelable("step", step);
//      bundle.putInt("position", stepPosition);
////      bundle.putParcelable("recipeIntent", recipe);
//
//      final Intent newIntent = new Intent(this, ViewRecipeActivity.class);
//      newIntent.putExtras(bundle);
//      startActivity(newIntent);
//
//    }
//
//  }

//  @Override
//  protected void onSaveInstanceState(Bundle outState) {
//    super.onSaveInstanceState(outState);
//    outState.putParcelable("recipe",recipe);
//    outState.putInt("stepPosition",stepPosition);
//  }
}
