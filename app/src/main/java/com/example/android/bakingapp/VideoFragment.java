package com.example.android.bakingapp;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import java.io.IOException;

/**
 * Created by lianavklt on 02/07/2018.
 */

public class VideoFragment extends Fragment {

  private static final String TAG = VideoFragment.class.getName();
  private static final String PLAYER_POSITION = "player_position";
  private static final String STATE = "state";
  private PlayerView mPlayerView;
  private String videoUrl;
  private String thumbnailUrl;
  private SimpleExoPlayer mExoPlayer;
  private FrameLayout stepTextViewId;
  private long playerPosition;
  private boolean getPlayerWhenReady;


  public VideoFragment() {
  }

  public void setVideoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
  }

  public void setThumbnailUrl(String thumbnailUrl) {
    this.thumbnailUrl = thumbnailUrl;
  }

  @Override
  public void onStart() {
    super.onStart();
    if (Util.SDK_INT > 23) {
      initializePlayerView();
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    if ((Util.SDK_INT <= 23 || mExoPlayer == null)) {
      initializePlayerView();
    }
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (savedInstanceState != null) {
      playerPosition = savedInstanceState.getLong(PLAYER_POSITION);
      getPlayerWhenReady = savedInstanceState.getBoolean(STATE);
    }
    BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
    TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(
        bandwidthMeter);
    TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

    mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
    initializePlayer(videoUrl, bandwidthMeter);

    setRetainInstance(true);
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_video, container, false);

    mPlayerView = rootView.findViewById(R.id.playerView);
    LinearLayout.LayoutParams layoutParams = (LayoutParams) mPlayerView.getLayoutParams();
    changeConfigurationAccordingToOrientation(getResources().getConfiguration(), layoutParams);

    initializePlayerView();
    return rootView;
  }

  private void initializePlayerView() {
    if (videoUrl == null || videoUrl.equals("")) {
      mPlayerView.setVisibility(View.GONE);
      try {
        mPlayerView.setDefaultArtwork(MediaStore.Images.Media
            .getBitmap(getContext().getContentResolver(), Uri.parse(thumbnailUrl)));
      } catch (IOException | NullPointerException e) {

        Log.i(TAG, "No ThumbnailUrl is available");
      }
      return;
    }
    mPlayerView.requestFocus();

    mPlayerView.setPlayer(mExoPlayer);

    mPlayerView.setVisibility(View.VISIBLE);
  }

  @Override
  public void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    playerPosition = mExoPlayer != null ? mExoPlayer.getCurrentPosition() : 0;
    outState.putLong(PLAYER_POSITION, playerPosition);
    getPlayerWhenReady = mExoPlayer != null && mExoPlayer.getPlayWhenReady();
    outState.putBoolean(STATE, getPlayerWhenReady);

  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    LinearLayout.LayoutParams params = (LayoutParams) mPlayerView.getLayoutParams();
    changeConfigurationAccordingToOrientation(newConfig, params);
  }

  private void changeConfigurationAccordingToOrientation(Configuration newConfig,
      LayoutParams params) {

    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE && !videoUrl.equals("")) {
      params.width = ViewGroup.LayoutParams.MATCH_PARENT;
      params.height = ViewGroup.LayoutParams.MATCH_PARENT;
      mPlayerView.setLayoutParams(params);

    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
      params.width = ViewGroup.LayoutParams.MATCH_PARENT;
      mPlayerView.setLayoutParams(params);
    }
  }

  private void initializePlayer(String uri,
      BandwidthMeter bandwidthMeter) {

    Uri videoUri = Uri.parse(uri);
    mExoPlayer.seekTo(playerPosition);
    mExoPlayer.setPlayWhenReady(getPlayerWhenReady);

    // Prepare the MediaSource.
    String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
    DataSource.Factory factory = new DefaultDataSourceFactory(getActivity(), userAgent,
        (TransferListener<? super DataSource>) bandwidthMeter);

    MediaSource mediaSource = new ExtractorMediaSource.Factory(factory)
        .createMediaSource(videoUri);
    final LoopingMediaSource loopingSource = new LoopingMediaSource(mediaSource);

    mExoPlayer.prepare(loopingSource);


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

  public void setStepTextView(FrameLayout stepTextView) {
    this.stepTextViewId = stepTextView;
  }
}
