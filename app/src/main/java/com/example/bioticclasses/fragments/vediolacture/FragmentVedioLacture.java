package com.example.bioticclasses.fragments.vediolacture;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bioticclasses.R;
import com.example.bioticclasses.Service.YoutubeConfig;
import com.example.bioticclasses.databinding.FragmentVedioLactureFragmentBinding;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

public class FragmentVedioLacture extends Fragment  {
    FragmentVedioLactureFragmentBinding binding;
    private FragmentVedioLactureViewModel mViewModel;
    TextView title;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    YouTubePlayerView inital;
    YouTubePlayerView initialize;

    public static FragmentVedioLacture newInstance() {
        return new FragmentVedioLacture();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentVedioLactureFragmentBinding.inflate(inflater, container, false);
        title = getActivity().findViewById(R.id.title);
        title.setText("Lactures");
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FragmentVedioLactureViewModel.class);
        // TODO: Use the ViewModel

    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initialize = (YouTubePlayerView) view.findViewById(R.id.youtubePlay);

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("5sYsSrvWnqQ");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };


//        inital.initialize(YoutubeConfig.getApiKey() , onInitializedListener);


    }


}
























