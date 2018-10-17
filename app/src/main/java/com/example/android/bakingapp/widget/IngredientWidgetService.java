package com.example.android.bakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by lianavklt on 04/07/2018.
 */

public class IngredientWidgetService extends RemoteViewsService {

  @Override
  public RemoteViewsFactory onGetViewFactory(Intent intent) {
    return (new ListProvider(this.getApplicationContext(), intent));
  }

}
