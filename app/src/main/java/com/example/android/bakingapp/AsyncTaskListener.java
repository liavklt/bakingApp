package com.example.android.bakingapp;

/**
 * Created by lianavklt on 06/07/2018.
 */

public interface AsyncTaskListener<T> {

  void onTaskPreExecute();

  T onTaskGetResult(int position);

  void onTaskComplete(T result);
}
