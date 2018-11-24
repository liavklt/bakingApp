package com.example.android.bakingapp.widget;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.android.bakingapp.model.Recipe;

/**
 * Created by lianavklt on 23/11/2018.
 */

public class RecipePreferencesHelper {

  public static final String PREFS_NAME = "prefs";

  public static void saveRecipe(Context context, Recipe recipe) {
    SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        .edit();
    prefs.putString("recipeKey", Recipe.toBase64String(recipe));

    prefs.apply();
  }

  public static Recipe loadRecipe(Context context) {
    SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    String recipeBase64 = prefs.getString("recipeKey", "");

    return "".equals(recipeBase64) ? null : Recipe.fromBase64(prefs.getString("recipeKey", ""));
  }
}
