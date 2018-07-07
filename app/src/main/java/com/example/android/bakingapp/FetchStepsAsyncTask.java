package com.example.android.bakingapp;

import android.content.Context;
import android.os.AsyncTask;
import com.example.android.bakingapp.model.Step;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by lianavklt on 06/07/2018.
 */

class FetchStepsAsyncTask extends AsyncTask<Integer, Void, List<Step>> {


  private WeakReference<Context> context;
  private AsyncTaskListener<List<Step>> asyncTaskListener;


  public FetchStepsAsyncTask(Context context,
      AsyncTaskListener<List<Step>> asyncTaskListener) {
    this.context = new WeakReference<>(context);
    this.asyncTaskListener = asyncTaskListener;
  }

  @Override
  protected List<Step> doInBackground(Integer... position) {

    return asyncTaskListener.onTaskGetResult(position[0]);
  }

  @Override
  protected void onPostExecute(List<Step> steps) {
    super.onPostExecute(steps);
    asyncTaskListener.onTaskComplete(steps);
  }

}
