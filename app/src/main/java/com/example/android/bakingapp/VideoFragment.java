package com.example.android.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by lianavklt on 02/07/2018.
 */

public class VideoFragment extends Fragment {
  private String videoUrl;
  ViewRecipeActivity a;
  SimpleExoPlayerView mPlayerView;
  private SimpleExoPlayer mExoPlayer;



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

    mPlayerView = rootView.findViewById(R.id.playerView);
    initializePlayer(Uri.parse(videoUrl));


    return rootView;
  }

  private void initializePlayer(Uri uri) {
    if(mExoPlayer == null){
      TrackSelector trackSelector = new DefaultTrackSelector();
      LoadControl loadControl = new DefaultLoadControl();
      mExoPlayer = ExoPlayerFactory.newSimpleInstance(a, trackSelector, loadControl);
      mPlayerView.setPlayer(mExoPlayer);
      // Prepare the MediaSource.
      String userAgent = Util.getUserAgent(a, "BakingApp");
      MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(
          a, userAgent), new DefaultExtractorsFactory(), null, null);
      mExoPlayer.prepare(mediaSource);
      mExoPlayer.setPlayWhenReady(true);
    }
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    releasePlayer();
  }

  private void releasePlayer() {
    mExoPlayer.stop();
    mExoPlayer.release();
    mExoPlayer = null;
  }
}
