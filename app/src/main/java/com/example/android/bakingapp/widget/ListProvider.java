package com.example.android.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
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

public class ListProvider implements RemoteViewsFactory {

  private Context context = null;
  private int appWidgetId;
  private List<ListItem> listItemList = new ArrayList<>();

  public ListProvider(Context context, Intent intent, int appWidgetId) {
    this.context = context;
    this.appWidgetId = appWidgetId;
    populateListItem();
  }

  private void populateListItem() {

    Uri ingredientUri = IngredientsContract.BASE_CONTENT_URI.buildUpon()
        .appendPath(IngredientsContract.PATH_INGREDIENTS).build();
    Cursor cursor = context.getContentResolver()
        .query(ingredientUri, null, null, null, null);
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
