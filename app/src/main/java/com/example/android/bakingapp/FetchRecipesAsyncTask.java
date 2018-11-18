package com.example.android.bakingapp;

import android.os.AsyncTask;
import com.example.android.bakingapp.model.Recipe;
import java.net.URL;
import java.util.List;

/**
 * Created by lianavklt on 17/07/2018.
 */

class FetchRecipesAsyncTask extends AsyncTask<URL, Void, List<Recipe>> {


  private AsyncTaskListener<List<Recipe>> asyncTaskListener;


  public FetchRecipesAsyncTask(AsyncTaskListener<List<Recipe>> asyncTaskListener) {
    this.asyncTaskListener = asyncTaskListener;
  }

  @Override
  protected List<Recipe> doInBackground(URL... urls) {
    URL recipeRequestURL = urls[0];
    return asyncTaskListener.onTaskGetResult(recipeRequestURL);

  }

  @Override
  protected void onPostExecute(List<Recipe> recipes) {
    super.onPostExecute(recipes);
    asyncTaskListener.onTaskComplete(recipes);

  }
}
