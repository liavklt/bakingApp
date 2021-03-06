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
import android.util.Log;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.android.bakingapp.data.IngredientsContract.IngredientsEntry;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

// This activity is responsible for displaying the master list of all recipes
// Implement the MasterListFragment callback, OnTextClickListener
public class MasterListActivity extends AppCompatActivity implements
    LoaderManager.LoaderCallbacks<Cursor>, MasterListAdapter.OnTextClickListener {

  private static final int TASK_LOADER_ID = 0;
  private static final String TAG = MasterListActivity.class.getSimpleName();

  @BindView(R.id.steps_recycler_view)
  RecyclerView recyclerView;
  @BindView(R.id.rv_ingredients)
  RecyclerView ingredientsRecyclerView;

  LinearLayoutManager linearLayoutManager;
  private CustomCursorAdapter adapter;
  private boolean mTwoPane;
  private int stepPosition;
  private Step step;
  private Recipe recipe;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (savedInstanceState != null) {
      stepPosition = savedInstanceState.getInt("stepPosition");
      recipe = savedInstanceState.getParcelable("recipe");
    }
    setContentView(R.layout.activity_select_recipe);
    this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    ButterKnife.bind(this);

    Intent intent = getIntent();
    if (intent == null) {

      finish();
    }
    recipe = intent.getParcelableExtra("recipe");

    ingredientsRecyclerView.setHasFixedSize(true);
    linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
        false);
    ingredientsRecyclerView.setLayoutManager(linearLayoutManager);
    adapter = new CustomCursorAdapter(this);
    ingredientsRecyclerView.setAdapter(adapter);
    getSupportLoaderManager().initLoader(TASK_LOADER_ID, null, this);

    MasterListFragment masterListFragment = new MasterListFragment();
    masterListFragment.initializeRecyclerView(recyclerView, recipe);

    if (findViewById(R.id.view_recipe_linear_layout) != null) {
      mTwoPane = true;

      if (savedInstanceState == null) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        ViewRecipeFragment viewRecipeFragment = new ViewRecipeFragment();
        step = recipe.getSteps().get(0);

        viewRecipeFragment.setDescription(
            step.getDescription());
        viewRecipeFragment.setVideoUrl(step != null ? step.getVideoUrl() : "");
        viewRecipeFragment.setOnTablet(true);
        VideoFragment videoFragment = new VideoFragment();
        videoFragment.setVideoUrl(step != null ? step.getVideoUrl() : "");
        videoFragment.setThumbnailUrl(step != null ? step.getThumbnailUrl() : "");

        videoFragment
            .setStepTextView((FrameLayout) findViewById(R.id.step_instructions_container));
        fragmentManager.beginTransaction().add(R.id.video_container, videoFragment).commit();

        fragmentManager.beginTransaction()
            .add(R.id.step_instructions_container, viewRecipeFragment)
            .commit();

      }
    } else {
      // We're in single-pane mode and displaying fragments on a phone in separate activities
      mTwoPane = false;

    }


  }


  public void onTextSelected(int position) {
    stepPosition = position;
    if (mTwoPane) {
      step = recipe.getSteps().get(position);
      ViewRecipeFragment viewRecipeFragment = new ViewRecipeFragment();
      viewRecipeFragment.setDescription(step.getDescription());
      viewRecipeFragment.setVideoUrl(step != null ? step.getVideoUrl() : "");
      viewRecipeFragment.setOnTablet(true);

      VideoFragment videoFragment = new VideoFragment();
      videoFragment.setVideoUrl(step != null ? step.getVideoUrl() : "");
      videoFragment.setThumbnailUrl(step != null ? step.getThumbnailUrl() : "");

      videoFragment.setStepTextView((FrameLayout) findViewById(R.id.step_instructions_container));
      getSupportFragmentManager().beginTransaction().replace(R.id.video_container, videoFragment)
          .commit();
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.step_instructions_container, viewRecipeFragment).commit();

    } else {

      Bundle bundle = new Bundle();
      bundle.putParcelable("step", recipe.getSteps().get(position));
      bundle.putParcelable("recipeIntent", recipe);
      bundle.putInt("position", position);

      final Intent newIntent = new Intent(this, ViewRecipeActivity.class);
      newIntent.putExtras(bundle);
      startActivity(newIntent);

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
                  new String[]{String.valueOf(recipe.getId())},
                  null);
        } catch (Exception e) {
          Log.e(TAG, "Failed to asynchronously load data.");
          Log.e(TAG, e.getMessage());
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

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelable("recipe", recipe);
    outState.putInt("stepPosition", stepPosition);
  }

}
