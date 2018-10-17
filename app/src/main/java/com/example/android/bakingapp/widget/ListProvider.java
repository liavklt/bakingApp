package com.example.android.bakingapp.widget;

import static com.example.android.bakingapp.data.IngredientsContract.IngredientsEntry.COLUMN_RECIPE_ID;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;
import com.example.android.bakingapp.MainActivity;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.IngredientsContract;
import com.example.android.bakingapp.data.IngredientsContract.IngredientsEntry;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lianavklt on 10/07/2018.
 */

class ListProvider implements RemoteViewsFactory {

  private Context context = null;
  private List<ListItem> listItemList = new ArrayList<>();


  ListProvider(Context context, Intent intent) {
    this.context = context;
    populateListItem();
  }

  private void populateListItem() {

    Uri ingredientUri = IngredientsContract.BASE_CONTENT_URI.buildUpon()
        .appendPath(IngredientsContract.PATH_INGREDIENTS).build();
    SharedPreferences sharedPreferences = context
        .getSharedPreferences("RecipePreference", Context.MODE_PRIVATE);
    int recipeKey = sharedPreferences.getInt("recipeKey", -1);
    Cursor cursor = context.getContentResolver()
        .query(ingredientUri, null, COLUMN_RECIPE_ID + "=?",
            new String[]{String.valueOf(recipeKey)}, null);
    try {
      if (cursor != null) {
        while (cursor.moveToNext()) {
          ListItem listItem = new ListItem();
          int recipeNameIndex = cursor.getColumnIndex(IngredientsEntry.COLUMN_RECIPE_NAME);
          int ingredientsIndex = cursor.getColumnIndex(IngredientsEntry.COLUMN_INGREDIENT);
          listItem.heading = cursor.getString(recipeNameIndex);
          listItem.content = cursor.getString(ingredientsIndex).replaceAll(",", "\n");
          listItemList.add(listItem);
        }
      }
    } finally {
      cursor.close();
    }
  }

  @Override
  public void onCreate() {

  }

  @Override
  public void onDataSetChanged() {

  }

  @Override
  public void onDestroy() {

  }

  @Override
  public int getCount() {
    return listItemList.size();
  }

  @Override
  public RemoteViews getViewAt(int position) {
    listItemList.clear();
    populateListItem();
    final RemoteViews remoteView = new RemoteViews(
        context.getPackageName(), R.layout.list_row);
    ListItem listItem = listItemList.get(position);
    remoteView.setTextViewText(R.id.heading, listItem.heading);
    remoteView.setTextViewText(R.id.content, listItem.content);
    Intent intent = new Intent(context, MainActivity.class);

    remoteView.setOnClickFillInIntent(R.id.content, intent);

    return remoteView;
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
