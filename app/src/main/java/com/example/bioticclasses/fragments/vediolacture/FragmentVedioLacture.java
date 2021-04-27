package com.example.bioticclasses.fragments.vediolacture;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bioticclasses.Adapter.VideoAdapter;
import com.example.bioticclasses.R;
import com.example.bioticclasses.Service.ApiClient;
import com.example.bioticclasses.Service.BiotechInterface;
import com.example.bioticclasses.databinding.FragmentVedioLactureFragmentBinding;
import com.example.bioticclasses.modal.video.VideoList;
import com.example.bioticclasses.other.SessionManage;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentVedioLacture extends Fragment {
    FragmentVedioLactureFragmentBinding binding;
    private FragmentVedioLactureViewModel mViewModel;
    TextView title;
    private static final String TAG = "FragmentVedioLacture";
    private YouTubePlayerView youTubeView;
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    String YOUTUBE_VIDEO_CODE = "Vx7YkKpt-J4";
    BiotechInterface biotechInterface;
    SessionManage sessionManage;
    JsonObject finalsubject;


    public static FragmentVedioLacture newInstance() {
        return new FragmentVedioLacture();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentVedioLactureFragmentBinding.inflate(inflater, container, false);
        title = getActivity().findViewById(R.id.title);
        title.setText("Lectures");
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FragmentVedioLactureViewModel.class);
        // TODO: Use the ViewModel
        biotechInterface = ApiClient.getClient().create(BiotechInterface.class);
        sessionManage = new SessionManage(requireContext());

        if (sessionManage.getUserDetails().get("CurrentSubject") != null) {
            try {
                JSONObject jsonObject = new JSONObject(sessionManage.getUserDetails().get("CurrentSubject"));
                Iterator<?> keys = jsonObject.keys();
                while (keys.hasNext()) {
                    String key = String.valueOf(keys.next());
                    finalsubject = new JsonObject();
//                finalsubject.addProperty("subject_id","606daed1aa5f48522a2170cb");
                    finalsubject.addProperty("subject_id", key);
                    finalsubject.addProperty("stu_clas", sessionManage.getUserDetails().get("Class"));

                }

                video();
            } catch (JSONException e) {
                Log.e(TAG, "video: " + e.getMessage());
                binding.progress.setVisibility(View.GONE);
            }

        } else {

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(sessionManage.getUserDetails().get("Subject"));
                Iterator<?> keys = jsonObject.keys();
                while (keys.hasNext()) {
                    String key1 = String.valueOf(keys.next());
                    finalsubject = new JsonObject();
                    finalsubject.addProperty("subject_id", key1);
                    finalsubject.addProperty("stu_clas", sessionManage.getUserDetails().get("Class"));
                    break;
                }

                video();


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


    }


    private void video() {
        biotechInterface.VIDEO_LIST_CALL(finalsubject).enqueue(new Callback<VideoList>() {
            @Override
            public void onResponse(Call<VideoList> call, Response<VideoList> response) {
                Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    if (!response.body().getResult().getError()) {
                        if(response.body().getResult().getData().isEmpty()){
                            binding.noDataFound.setVisibility(View.VISIBLE);
                            binding.progress.setVisibility(View.GONE);
                        }else {
                            binding.videoRecyclerView.setAdapter(new VideoAdapter(response.body().getResult().getData()));
                            binding.progress.setVisibility(View.GONE);
                        }

                        return;
                    }
                    Toast.makeText(requireContext(), response.body().getResult().getMessage(), Toast.LENGTH_SHORT).show();
                    binding.noDataFound.setVisibility(View.VISIBLE);
                    binding.progress.setVisibility(View.GONE);
                    return;
                }
                binding.noDataFound.setVisibility(View.VISIBLE);
                binding.progress.setVisibility(View.GONE);
                Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<VideoList> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                binding.noDataFound.setVisibility(View.VISIBLE);
                binding.progress.setVisibility(View.GONE);
            }
        });
    }

    private void Defvideo(String key) {
        biotechInterface.VIDEO_LIST_CALL(finalsubject).enqueue(new Callback<VideoList>() {
            @Override
            public void onResponse(Call<VideoList> call, Response<VideoList> response) {
                Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    if (!response.body().getResult().getError()) {
                        binding.videoRecyclerView.setAdapter(new VideoAdapter(response.body().getResult().getData()));
                        binding.progress.setVisibility(View.GONE);
                        return;
                    }
                    Toast.makeText(requireContext(), response.body().getResult().getMessage(), Toast.LENGTH_SHORT).show();
                    binding.noDataFound.setVisibility(View.VISIBLE);
                    binding.progress.setVisibility(View.GONE);
                    return;
                }
                binding.noDataFound.setVisibility(View.VISIBLE);
                binding.progress.setVisibility(View.GONE);
                Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<VideoList> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                binding.noDataFound.setVisibility(View.VISIBLE);
                binding.progress.setVisibility(View.GONE);
            }
        });
    }


}

























