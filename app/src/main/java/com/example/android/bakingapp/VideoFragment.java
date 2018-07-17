package com.example.android.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    initializePlayer(Uri.parse(videoUrl));

    return rootView;
  }

  private void initializePlayer(Uri uri) {
    if (uri == null) {
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
    mExoPlayer.stop();
    mExoPlayer.release();
    mExoPlayer = null;
  }
}
