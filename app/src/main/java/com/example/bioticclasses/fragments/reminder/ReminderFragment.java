package com.example.bioticclasses.fragments.reminder;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bioticclasses.Adapter.AttendanceAdapter;
import com.example.bioticclasses.Adapter.ReminderAdapter;
import com.example.bioticclasses.R;
import com.example.bioticclasses.Service.ApiClient;
import com.example.bioticclasses.Service.BiotechInterface;
import com.example.bioticclasses.databinding.ReminderFragmentBinding;
import com.example.bioticclasses.modal.reminder.Remainder;
import com.example.bioticclasses.other.NetworkCheck;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReminderFragment extends Fragment {

    private ReminderViewModel mViewModel;
    ReminderFragmentBinding binding;
    BiotechInterface biotechInterface;
    private static final String TAG = "ReminderFragment";
    TextView title;


    public static ReminderFragment newInstance() {
        return new ReminderFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ReminderFragmentBinding.inflate(inflater , container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        title=getActivity().findViewById(R.id.title);
        title.setText("Notification");

        mViewModel = new ViewModelProvider(this).get(ReminderViewModel.class);
        biotechInterface = ApiClient.getClient().create(BiotechInterface.class);

        // TODO: Use the ViewModel

        if(new NetworkCheck().haveNetworkConnection(getActivity())){
            fetch_reminder();
        }else {
            Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }


    }




    private void fetch_reminder(){
        biotechInterface.fetch_reminder().enqueue(new Callback<Remainder>() {
            @Override
            public void onResponse(Call<Remainder> call, Response<Remainder> response) {
                Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                if(response.isSuccessful()){
                    if(!response.body().getError()){
                        if (response.body().getData().size() > 0){
                            binding.ReminderRecyclerView.setAdapter(new ReminderAdapter(response.body().getData(),getActivity()));
                            binding.noDataFound.setVisibility(View.GONE);
                            binding.progress.setVisibility(View.GONE);
                            return;
                        }
                        binding.noDataFound.setVisibility(View.VISIBLE);
                        binding.progress.setVisibility(View.GONE);

                        return;
                    }
                    Toast.makeText(requireContext(), ""+ response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    binding.noDataFound.setVisibility(View.VISIBLE);
                    binding.progress.setVisibility(View.GONE);
                    return;
                }
            }

            @Override
            public void onFailure(Call<Remainder> call, Throwable t) {

            }
        });

    }

}