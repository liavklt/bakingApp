package com.example.android.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.example.android.bakingapp.MasterListActivity;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.IngredientsContract;
import com.example.android.bakingapp.data.IngredientsContract.IngredientsEntry;

/**
 * Created by lianavklt on 04/07/2018.
 */

public class IngredientWidgetService extends RemoteViewsService {

  @Override
  public RemoteViewsFactory onGetViewFactory(Intent intent) {
    return new IngredientsRemoteViewsFactory(this.getApplicationContext());
  }

  class IngredientsRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context context;
    Cursor mCursor;


    public IngredientsRemoteViewsFactory(
        Context applicationContext) {
      context = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

      Uri INGREDIENT_URI = IngredientsContract.BASE_CONTENT_URI.buildUpon()
          .appendPath(IngredientsContract.PATH_INGREDIENTS).build();
      if (mCursor != null) {
        mCursor.close();
      }
      mCursor = context.getContentResolver()
          .query(INGREDIENT_URI, null, null, null, IngredientsEntry.COLUMN_RECIPE_NAME);
    }

    @Override
    public void onDestroy() {

      mCursor.close();
    }

    @Override
    public int getCount() {
      if (mCursor == null) {
        return 0;
      }
      return mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {

      if (mCursor == null || mCursor.getCount() == 0) {
        return null;
      }
      mCursor.moveToPosition(position);
      int recipeNameIndex = mCursor.getColumnIndex(IngredientsEntry.COLUMN_RECIPE_NAME);
      int ingredientsIndex = mCursor.getColumnIndex(IngredientsEntry.COLUMN_INGREDIENT);
      int recipeIdIndex = mCursor.getColumnIndex(IngredientsEntry.COLUMN_RECIPE_ID);

      String recipeName = mCursor.getString(recipeNameIndex);
      String allIngredients = mCursor.getString(ingredientsIndex);
      int recipeId = mCursor.getInt(recipeIdIndex);

      RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

      views.setTextViewText(R.id.widget_recipe_name, recipeName);
      views.setTextViewText(R.id.widget_recipe_ingredients, allIngredients);

      Bundle extras = new Bundle();
      extras.putLong(MasterListActivity.EXTRA_RECIPE_ID, recipeId);
      Intent fillInIntent = new Intent();
      fillInIntent.putExtras(extras);
      views.setOnClickFillInIntent(R.id.widget_recipe_ingredients, fillInIntent);
      return views;
    }

    @Override
    public RemoteViews getLoadingView() {
      return null;
    }

    @Override
    public int getViewTypeCount() {
      return 1;
    }

    @Override
    public long getItemId(int position) {
      return position;
    }

    @Override
    public boolean hasStableIds() {
      return true;
    }
  }
}
