package com.example.android.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;

/**
 * Created by lianavklt on 02/07/2018.
 */

public class VideoFragment extends Fragment implements OnPreparedListener {
  private String videoUrl;
  ViewRecipeActivity a;
  VideoView mPlayerView;



  public String getVideoUrl() {
    return videoUrl;
  }

  public void setVideoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
  }

  public VideoFragment() {
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
    View rootView = inflater.inflate(R.layout.fragment_video, container, false);

    mPlayerView = rootView.findViewById(R.id.video_view);
    if (videoUrl!=null && !videoUrl.equals("")) {
      mPlayerView.setOnPreparedListener(this);
      mPlayerView.setVideoURI(Uri.parse(videoUrl));
    }else{
      mPlayerView.setVisibility(View.GONE);
    }


    return rootView;
  }


  @Override
  public void onDestroy() {
    super.onDestroy();
  }



  @Override
  public void onPrepared() {

    if (mPlayerView.getVisibility()!=View.GONE) {
      mPlayerView.start();
    }
  }
}
