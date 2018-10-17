package com.example.android.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.Toast;
import com.example.android.bakingapp.MainActivity;
import com.example.android.bakingapp.R;

/**
 * Created by lianavklt on 03/07/2018.
 */

public class IngredientWidgetProvider extends AppWidgetProvider {

  @Override
  public void onReceive(Context context, Intent intent) {
    Toast.makeText(context, "Action:" + intent.getAction(), Toast.LENGTH_SHORT).show();
    SharedPreferences sharedPreferences = context
        .getSharedPreferences("RecipePreference", Context.MODE_PRIVATE);
    int recipeKey = sharedPreferences.getInt("recipeKey", -1);
    if (recipeKey != -1) {
      AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
      int[] appWidgetIds = appWidgetManager
          .getAppWidgetIds(new ComponentName(context, IngredientWidgetProvider.class));

      onUpdate(context, appWidgetManager, appWidgetIds);

    }


  }

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

    for (int appWidgetId : appWidgetIds) {
      RemoteViews remoteViews = updateWidgetListView(context,
          appWidgetId);
      appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_list_view);

      appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }
    super.onUpdate(context, appWidgetManager, appWidgetIds);

  }

  private RemoteViews updateWidgetListView(Context context, int appWidgetId) {
    RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
        R.layout.widget_grid_view);

    //RemoteViews Service needed to provide adapter for ListView
    Intent svcIntent = new Intent(context, IngredientWidgetService.class);
    int appWidgetIds[] = AppWidgetManager.getInstance(context)
        .getAppWidgetIds(new ComponentName(context, IngredientWidgetProvider.class));

    //passing app widget id to that RemoteViews Service
    svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

    //setting adapter to listview of the widget
    remoteViews.setRemoteAdapter(appWidgetId, R.id.widget_list_view,
        svcIntent);
    //setting an empty view in case of no data
    remoteViews.setEmptyView(R.id.widget_list_view, R.id.empty_view);

    Intent startActivityIntent = new Intent(context, MainActivity.class);
    PendingIntent startActivityPendingIntent = PendingIntent
        .getActivity(context, 0, startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    remoteViews.setPendingIntentTemplate(R.id.widget_list_view, startActivityPendingIntent);

    return remoteViews;
  }
}
