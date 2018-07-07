package com.example.android.bakingapp.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.widget.RemoteViews;
import com.example.android.bakingapp.MasterListActivity;
import com.example.android.bakingapp.R;

/**
 * Created by lianavklt on 03/07/2018.
 */

public class IngredientWidgetProvider extends AppWidgetProvider {

  public static final String EXTRA_ITEM = "com.example.android.bakingapp.EXTRA_ITEM";
  public static final String TOAST_ACTION = "com.example.android.bakingapp.TOAST_ACTION";


  @TargetApi(VERSION_CODES.JELLY_BEAN)
  static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
      int appWidgetId) {
    Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);

    RemoteViews rv;

    rv = getBakingAppGridRemoteView(context);

    appWidgetManager.updateAppWidget(appWidgetId, rv);

//    Intent intent = new Intent(context, MainActivity.class);
//    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//    // Construct the RemoteViews object
//    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
//    // Widgets allow click handlers to only launch pending intents
//    views.setOnClickPendingIntent(R.id.widget_recipe_ingredients, pendingIntent);
//    // Instruct the widget manager to update the widget
//    appWidgetManager.updateAppWidget(appWidgetId, views);

    // Create an Intent to launch MainActivity when clicked
//    Intent intent = new Intent(context, IngredientWidgetService.class);
//    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
//    intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
//
//    // Construct the RemoteViews object
//    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
//    // Widgets allow click handlers to only launch pending intents
//    views.setRemoteAdapter(appWidgetId, R.id.stack_view,intent);
//
////    views.setEmptyView(R.id.stack_view,R.id.empty_view);
//    // Instruct the widget manager to update the widget
//
//    Intent toastIntent = new Intent(context, IngredientWidgetProvider.class);
//    toastIntent.setAction(IngredientWidgetProvider.TOAST_ACTION);
//    toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
//    intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
//    PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent,
//        PendingIntent.FLAG_UPDATE_CURRENT);
//    views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);
//    appWidgetManager.updateAppWidget(appWidgetId, views);

  }

  private static RemoteViews getBakingAppGridRemoteView(Context context) {

    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_grid_view);
    Intent intent = new Intent(context, IngredientWidgetService.class);
    views.setRemoteAdapter(R.id.widget_grid_view, intent);
    // Set the intent to launch when clicked
    Intent appIntent = new Intent(context, MasterListActivity.class);
    PendingIntent appPendingIntent = PendingIntent
        .getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    views.setPendingIntentTemplate(R.id.widget_grid_view, appPendingIntent);
    // Handle empty gardens
    views.setEmptyView(R.id.widget_grid_view, R.id.empty_view);
    return views;
  }


  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    for (int appWidgetId : appWidgetIds) {
      updateAppWidget(context, appWidgetManager, appWidgetId);
    }

  }
}
