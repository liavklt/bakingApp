package com.example.android.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.android.bakingapp.model.Step;

/**
 * Created by lianavklt on 26/06/2018.
 */

public class ViewRecipeFragment extends Fragment {

  private String videoUrl;

  public String getVideoUrl() {
    return videoUrl;
  }

  public void setVideoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
  }

  ViewRecipeActivity a;

  private String description;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ViewRecipeFragment() {
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);

    if(context instanceof ViewRecipeActivity){
      a = (ViewRecipeActivity) context;
    }

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
