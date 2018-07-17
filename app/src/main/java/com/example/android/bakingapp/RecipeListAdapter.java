package com.example.android.bakingapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.android.bakingapp.model.Recipe;
import java.util.List;

/**
 * Created by lianavklt on 23/06/2018.
 */

public class RecipeListAdapter extends BaseAdapter {

  private Context mContext;
  private List<Recipe> mRecipes;


  public RecipeListAdapter(Context context) {
    mContext = context;
  }


  public void setRecipeData(List<Recipe> recipeData) {
    this.mRecipes = recipeData;
  }

  @Override
  public int getCount() {
    if (mRecipes == null) {
      return 0;
    }
    return mRecipes.size();
  }

  @Override
  public Object getItem(int i) {
    return null;
  }

  @Override
  public long getItemId(int i) {
    return 0;
  }

  public View getView(final int position, View convertView, ViewGroup parent) {
    TextView textView;
    if (convertView == null) {
      textView = new TextView(mContext);
      textView.setPadding(8, 8, 8, 8);
    } else {
      textView = (TextView) convertView;
    }

    textView.setText(mRecipes.get(position).getName());
    textView.setTypeface(null, Typeface.BOLD);
    textView.setGravity(Gravity.CENTER);
    textView.setTextColor(Color.BLACK);
    textView.setTextSize(32);
    textView.setHeight(250);
    return textView;
  }
}
