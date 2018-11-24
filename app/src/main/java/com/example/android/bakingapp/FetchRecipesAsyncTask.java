package com.example.android.bakingapp;

import android.content.Context;
import android.os.AsyncTask;
import com.example.android.bakingapp.model.Recipe;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.List;

/**
 * Created by lianavklt on 17/07/2018.
 */

public class FetchRecipesAsyncTask extends AsyncTask<URL, Void, List<Recipe>> {


  private WeakReference<Context> context;
  private AsyncTaskListener<List<Recipe>> asyncTaskListener;


  public FetchRecipesAsyncTask(Context context,
      AsyncTaskListener<List<Recipe>> asyncTaskListener) {
    this.context = new WeakReference<>(context);
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
