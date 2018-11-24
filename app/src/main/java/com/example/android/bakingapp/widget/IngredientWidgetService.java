package com.example.android.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by lianavklt on 04/07/2018.
 */

public class IngredientWidgetService extends RemoteViewsService {

//  public static void updateWidget(Context context, Recipe recipe) {
//    RecipePreferencesHelper.saveRecipe(context, recipe);
//
//    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, IngredientWidgetProvider.class));
//    IngredientWidgetProvider.updateAppWidgets(context, appWidgetManager, appWidgetIds);
//  }

  @Override
  public RemoteViewsFactory onGetViewFactory(Intent intent) {
    int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
        AppWidgetManager.INVALID_APPWIDGET_ID);
    return (new ListProvider(this.getApplicationContext(), intent, appWidgetId));
  }

}
