package com.example.android.bakingapp;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.android.bakingapp.data.IngredientsContract.IngredientsEntry;

/**
 * Created by lianavklt on 06/07/2018.
 */

public class IngredientsFragment extends Fragment implements
    LoaderManager.LoaderCallbacks<Cursor> {

  String ingredients;
  private CustomCursorAdapter adapter;


  public IngredientsFragment() {
  }

  public String getIngredients() {
    return ingredients;
  }

  public void setIngredients(String ingredients) {
    this.ingredients = ingredients;
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    return inflater.inflate(R.layout.fragment_ingredients, container, false);

  }

  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    return new AsyncTaskLoader<Cursor>(getContext()) {

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
          return getActivity().getApplicationContext().getContentResolver()
              .query(IngredientsEntry.CONTENT_URI, null, null, null,
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
}
