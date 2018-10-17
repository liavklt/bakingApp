package com.example.android.bakingapp;

import android.content.res.Configuration;
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

  private TextView stepTv;
  private String videoUrl;
  private boolean isOnTablet;

  private String description;

  public ViewRecipeFragment() {
  }

  public void setOnTablet(boolean onTablet) {
    isOnTablet = onTablet;
  }

  public void setVideoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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

    stepTv = rootView.findViewById(R.id.step_textview);
    stepTv.setText(getDescription());
    changeConfigurationAccordingToOrientation(getResources().getConfiguration());

    return rootView;
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    changeConfigurationAccordingToOrientation(newConfig);
  }

  private void changeConfigurationAccordingToOrientation(Configuration newConfig) {

    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE && !videoUrl.equals("")
        && !isOnTablet) {
      stepTv.setVisibility(View.GONE);


    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
      stepTv.setVisibility(View.VISIBLE);

    } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE && videoUrl
        .equals("") && !isOnTablet) {
      stepTv.setVisibility(View.VISIBLE);
    }
  }
}
