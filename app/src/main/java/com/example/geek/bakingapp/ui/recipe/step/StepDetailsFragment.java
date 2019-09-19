package com.example.geek.bakingapp.ui.recipe.step;


import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.geek.bakingapp.data.Step;
import com.example.geek.bakingapp.databinding.FragmentStepDetailBinding;
import com.example.geek.bakingapp.ui.recipe.RecipeActivity;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.net.URLConnection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepDetailsFragment extends Fragment {

    private Step step;
    private FragmentStepDetailBinding binding;
    private SimpleExoPlayer exoPlayer;
    private String mVideoUrl;
    private String mThumbnailUrl;
    private boolean viewVideo;
    private int currentWindow;
    private long playbackPosition;
    private static final String CURRENT_WINDOW_INDEX = "current_window_index";
    private static final String PLAYBACK_POSITION = "playback_position";

    public StepDetailsFragment() {
        // Required empty public constructor
    }

    public static StepDetailsFragment newInstance(Step step) {

        Bundle args = new Bundle();

        args.putParcelable("step", Parcels.wrap(step));
        StepDetailsFragment fragment = new StepDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (!RecipeActivity.mTwoPane) {
            if (viewVideo) {
                if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    binding.exoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
                    binding.stepTextView.setVisibility(View.GONE);
                } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    binding.exoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
                    binding.stepTextView.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION, 0);
            currentWindow = savedInstanceState.getInt(CURRENT_WINDOW_INDEX, 0);
        }
        step = Parcels.unwrap(getArguments().getParcelable("step"));
        assert step != null;
        mVideoUrl = step.getVideoURL();
        mThumbnailUrl = step.getThumbnailURL();
        if (mVideoUrl != null && !mVideoUrl.isEmpty()) {
            viewVideo = true;
            mVideoUrl = step.getVideoURL();
        } else viewVideo = false;
        if (mThumbnailUrl != null && !mThumbnailUrl.isEmpty() && showThumbnail(mThumbnailUrl)) {
            Picasso.get().load(Uri.parse(mThumbnailUrl)).into(binding.thumbnailImg);
            binding.thumbnailImg.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer(Uri.parse(mVideoUrl));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (viewVideo && Util.SDK_INT <= 23) {
            initializePlayer(Uri.parse(mVideoUrl));
        } else binding.exoPlayerView.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23)
            releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23)
            releasePlayer();

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStepDetailBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.stepTextView.setText(step.getDescription());
        binding.executePendingBindings();
    }

    private void initializePlayer(Uri uri) {
        // exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultTrackSelector());
        exoPlayer = ExoPlayerFactory.newSimpleInstance
                (new DefaultRenderersFactory(getContext()), new DefaultTrackSelector(), new DefaultLoadControl());
        binding.exoPlayerView.setPlayer(exoPlayer);
        exoPlayer.setPlayWhenReady(true);
        exoPlayer.seekTo(currentWindow, playbackPosition);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory
                (getContext(), Util.getUserAgent(getContext(), "BakingApp"));
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
        exoPlayer.prepare(videoSource);

    }


    private void releasePlayer() {
        if (exoPlayer != null) {
            playbackPosition = exoPlayer.getCurrentPosition();
            currentWindow = exoPlayer.getCurrentWindowIndex();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (exoPlayer == null) {
            outState.putLong(PLAYBACK_POSITION, playbackPosition);
            outState.putInt(CURRENT_WINDOW_INDEX, currentWindow);
        }
    }

    private boolean showThumbnail(String s) {
        String fileType = URLConnection.guessContentTypeFromName(s);
        return fileType != null && fileType.startsWith("image");
    }


}
