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



  public RecipeListAdapter(Context context, List<Recipe> recipes) {
    mContext = context;
    mRecipes = recipes;
  }

  private enum Cakes {
    NUTELLA_PIE("Nutella Pie"), BROWNIES("Brownies"), YELLOW_CAKE("Yellow Cake"), CHEESECAKE(
        "Cheesecake");
    private String cakeName;

    Cakes(String cakeName) {
      this.cakeName = cakeName;

    }

    public String getCakeName() {
      return cakeName;
    }
  }
  @Override
  public int getCount() {
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
    textView.setTextColor(Color.WHITE);
    textView.setTextSize(32);
    textView.setBackgroundResource(getDrawableForRecipe(textView.getText()));
    return textView;
  }

  private static int getDrawableForRecipe(CharSequence text) {
    if (Cakes.BROWNIES.getCakeName().equals(text.toString())) {
      return R.drawable.brownie;
    } else if (Cakes.CHEESECAKE.getCakeName().equals(text.toString())) {
      return R.drawable.cheesecake;

    } else if (Cakes.NUTELLA_PIE.getCakeName().equals(text.toString())) {
      return R.drawable.nutella_pie;
    } else {
      return R.drawable.yellow_cake;
    }

  }

}
