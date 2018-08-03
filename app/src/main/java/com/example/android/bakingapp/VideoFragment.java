package com.example.android.bakingapp;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by lianavklt on 02/07/2018.
 */

public class VideoFragment extends Fragment {

  PlayerView mPlayerView;
  private String videoUrl;
  private SimpleExoPlayer mExoPlayer;
  private FrameLayout stepTextViewId;


  public VideoFragment() {
  }

  public String getVideoUrl() {
    return videoUrl;
  }

  public void setVideoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_video, container, false);

    mPlayerView = rootView.findViewById(R.id.playerView);
    LinearLayout.LayoutParams layoutParams = (LayoutParams) mPlayerView.getLayoutParams();
    changeConfigurationAccordingToOrientation(getResources().getConfiguration(), layoutParams);
    initializePlayer(Uri.parse(videoUrl));

    return rootView;
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    LinearLayout.LayoutParams params = (LayoutParams) mPlayerView.getLayoutParams();
    changeConfigurationAccordingToOrientation(newConfig, params);
  }

  private void changeConfigurationAccordingToOrientation(Configuration newConfig,
      LayoutParams params) {
    FrameLayout stepTv = stepTextViewId;

    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE && !videoUrl.equals("")) {
      stepTv.setVisibility(View.GONE);
      params.width = ViewGroup.LayoutParams.MATCH_PARENT;
      params.height = ViewGroup.LayoutParams.MATCH_PARENT;
      mPlayerView.setLayoutParams(params);


    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
      stepTv.setVisibility(View.VISIBLE);
      params.width = ViewGroup.LayoutParams.MATCH_PARENT;
      params.height = 600;
      mPlayerView.setLayoutParams(params);
    }
  }

  private void initializePlayer(Uri uri) {
    if (uri == null || uri.toString().equals("")) {
      mPlayerView.setVisibility(View.GONE);
      return;
    }
    mPlayerView.requestFocus();

    BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
    TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(
        bandwidthMeter);
    TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

    mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

    mPlayerView.setPlayer(mExoPlayer);
    mExoPlayer.setPlayWhenReady(true);

    // Prepare the MediaSource.
    String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
    DataSource.Factory factory = new DefaultDataSourceFactory(getActivity(), userAgent,
        (TransferListener<? super DataSource>) bandwidthMeter);
    MediaSource mediaSource = new ExtractorMediaSource.Factory(factory).createMediaSource(uri);
    final LoopingMediaSource loopingSource = new LoopingMediaSource(mediaSource);

    mExoPlayer.prepare(loopingSource);

    mPlayerView.setVisibility(View.VISIBLE);

  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    releasePlayer();
  }

  private void releasePlayer() {
    if (mExoPlayer != null) {
      mExoPlayer.stop();
      mExoPlayer.release();
      mExoPlayer = null;
    }
  }

  public FrameLayout getStepTextView() {
    return stepTextViewId;
  }

  public void setStepTextView(FrameLayout stepTextView) {
    this.stepTextViewId = stepTextView;
  }
}
