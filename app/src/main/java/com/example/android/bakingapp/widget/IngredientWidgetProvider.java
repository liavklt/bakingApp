package com.example.android.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import com.example.android.bakingapp.MasterListActivity;
import com.example.android.bakingapp.R;

/**
 * Created by lianavklt on 03/07/2018.
 */

public class IngredientWidgetProvider extends AppWidgetProvider {

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    final int N = appWidgetIds.length;

    for (int i = 0; i < N; ++i) {
      RemoteViews remoteViews = updateWidgetListView(context,
          appWidgetIds[i]);
      appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
    }
    super.onUpdate(context, appWidgetManager, appWidgetIds);

  }

  private RemoteViews updateWidgetListView(Context context, int appWidgetId) {
    RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
        R.layout.widget_grid_view);

    //RemoteViews Service needed to provide adapter for ListView
    Intent svcIntent = new Intent(context, IngredientWidgetService.class);
    //passing app widget id to that RemoteViews Service
    svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

    //setting adapter to listview of the widget
    remoteViews.setRemoteAdapter(appWidgetId, R.id.widget_list_view,
        svcIntent);
    //setting an empty view in case of no data
    remoteViews.setEmptyView(R.id.widget_list_view, R.id.empty_view);

    Intent startActivityIntent = new Intent(context, MasterListActivity.class);
    PendingIntent startActivityPendingIntent = PendingIntent
        .getActivity(context, 0, startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    remoteViews.setPendingIntentTemplate(R.id.widget_list_view, startActivityPendingIntent);

    return remoteViews;
  }
}
