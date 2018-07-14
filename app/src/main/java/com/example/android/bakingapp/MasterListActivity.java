package com.example.android.bakingapp;

import static com.example.android.bakingapp.data.IngredientsContract.IngredientsEntry.COLUMN_RECIPE_ID;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.android.bakingapp.data.IngredientsContract.IngredientsEntry;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import java.util.List;
import org.json.JSONException;
import utils.JsonUtils;

// This activity is responsible for displaying the master list of all recipes
// Implement the MasterListFragment callback, OnTextClickListener
public class MasterListActivity extends AppCompatActivity implements
    LoaderManager.LoaderCallbacks<Cursor> {

  public static final String EXTRA_POSITION = "extra_position";
  public static final int DEFAULT_POSITION = -1;
  public static final String EXTRA_RECIPE_ID = "com.example.android.bakingapp.extra.RECIPE_ID";
  private static final int TASK_LOADER_ID = 0;

  List<Recipe> allRecipes;
  @BindView(R.id.steps_recycler_view)
  RecyclerView recyclerView;
  @BindView(R.id.rv_ingredients)
  RecyclerView ingredientsRecyclerView;
  LinearLayoutManager linearLayoutManager;
  private CustomCursorAdapter adapter;
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

    ingredientsRecyclerView.setHasFixedSize(true);
    linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
        false);
    ingredientsRecyclerView.setLayoutManager(linearLayoutManager);
    adapter = new CustomCursorAdapter(this);
    ingredientsRecyclerView.setAdapter(adapter);
    getSupportLoaderManager().initLoader(TASK_LOADER_ID, null, this);

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


  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    return new AsyncTaskLoader<Cursor>(this) {

      Cursor mTaskData = null;

      @Override
      protected void onStartLoading() {
        if (mTaskData != null) {
          deliverResult(mTaskData);
        } else {
          forceLoad();
        }
      }

      @Override
      public Cursor loadInBackground() {
        try {
          return getContentResolver()
              .query(IngredientsEntry.CONTENT_URI, null, COLUMN_RECIPE_ID + "=?",
                  new String[]{String.valueOf(recipePosition + 1)},
                  null);
        } catch (Exception e) {
          System.out.println("Failed to asynchronously load data.");
          System.out.println(e.getMessage());
          return null;
        }
      }

      @Override
      public void deliverResult(Cursor data) {
        mTaskData = data;
        super.deliverResult(data);
      }
    };
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    adapter.swapCursor(data);

  }

  @Override
  public void onLoaderReset(@NonNull Loader<Cursor> loader) {
    adapter.swapCursor(null);


  }


//  @Override
//  protected void onSaveInstanceState(Bundle outState) {
//    super.onSaveInstanceState(outState);
//    outState.putParcelable("recipe",recipe);
//    outState.putInt("stepPosition",stepPosition);
//  }
}
