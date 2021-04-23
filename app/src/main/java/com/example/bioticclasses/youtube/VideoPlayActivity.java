package com.example.bioticclasses.youtube;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.bioticclasses.R;
import com.example.bioticclasses.Service.YoutubeConfig;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoPlayActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_DIALOG_REQUESTS = 1;
    String YOUTUBE_VIDEO_CODE;
    YouTubePlayerView youtubeView;
    public static final String KEY = "AIzaSyDDJ32MPL_1x9yFUcwsMuUJfhrvZnhsIzY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);


        youtubeView = findViewById(R.id.youtubeView);

        YOUTUBE_VIDEO_CODE = getIntent().getStringExtra("url");

        youtubeView.initialize(YoutubeConfig.getApiKey(), this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {

//          full screenset
//            youTubePlayer.setFullscreen(true);

            // loadVideo() will auto play video
            // Use cueVideo() method, if you don't want to play it automatically
            youTubePlayer.loadVideo(YOUTUBE_VIDEO_CODE);

            // Hiding player controls
//            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);


        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUESTS).show();
        } else {
            String errorMessage = String.format(
                    getString(R.string.error_player), errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUESTS) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(KEY, this);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }

}