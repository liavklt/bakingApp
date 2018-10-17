package com.example.android.bakingapp;

import java.net.URL;

/**
 * Created by lianavklt on 06/07/2018.
 */

interface AsyncTaskListener<T> {

  T onTaskGetResult(int position);

  T onTaskGetResult(URL url);


  void onTaskComplete(T result);
}
