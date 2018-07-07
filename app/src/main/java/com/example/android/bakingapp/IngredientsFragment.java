package com.example.android.bakingapp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by lianavklt on 06/07/2018.
 */

public class IngredientsFragment extends Fragment {

  MasterListActivity activity;
  String ingredients;

  public IngredientsFragment() {
  }

  public String getIngredients() {
    return ingredients;
  }

  public void setIngredients(String ingredients) {
    this.ingredients = ingredients;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof MasterListActivity) {
      activity = (MasterListActivity) context;
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);
    TextView ingredientsTv = rootView.findViewById(R.id.ingredients_textview);
    ingredientsTv.setText(getIngredients());
    return rootView;
  }

}
