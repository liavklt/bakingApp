package com.example.android.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by lianavklt on 26/06/2018.
 */

public class ViewRecipeFragment extends Fragment {

  ViewRecipeActivity a;

  private String description;

  public ViewRecipeFragment() {
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);

    if(context instanceof ViewRecipeActivity){
      a = (ViewRecipeActivity) context;
    }

  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }

  @Override
  public View onCreateView(LayoutInflater inflater,  ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_view_recipe, container, false);

    TextView stepTv = rootView.findViewById(R.id.step_textview);
    stepTv.setText(getDescription());

    return rootView;
  }
}
