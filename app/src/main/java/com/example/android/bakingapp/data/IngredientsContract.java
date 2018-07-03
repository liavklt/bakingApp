package com.example.android.bakingapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by lianavklt on 03/07/2018.
 */

public class IngredientsContract {

  public static final String AUTHORITY="com.example.android.bakingapp";
  public static final Uri BASE_CONTENT_URI= Uri.parse("content://"+AUTHORITY);
  public static final String PATH_INGREDIENTS="ingredients";


  public static final class IngredientsEntry implements BaseColumns{
    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENTS).build();

    public static final String TABLE_NAME="ingredients";
    public static final String COLUMN_ID="id";
    public static final String COLUMN_RECIPE_ID="recipeId";
    public static final String COLUMN_RECIPE_NAME="recipeName";
    public static final String COLUMN_INGREDIENT="ingredient";
  }
}
