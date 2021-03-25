package com.example.bioticclasses.repositories;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bioticclasses.Service.ApiClient;
import com.example.bioticclasses.Service.BiotechInterface;
import com.example.bioticclasses.global.GlobalList;
import com.example.bioticclasses.modal.mainList.Datum;
import com.example.bioticclasses.modal.mainList.MainList;
import com.example.bioticclasses.other.SessionManage;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityRep {

    MutableLiveData<List<Datum>> datumMutableLiveData = new MutableLiveData<>();
    BiotechInterface biotechInterface;
    SessionManage sessionManage;
    Context context;
    public static MainActivityRep ourInstance;
    private static final String TAG = "MainActivityRep";

    public static MainActivityRep getInstance(Context context) {
        return ourInstance = new MainActivityRep(context);
    }

    public MainActivityRep(Context context) {
        this.biotechInterface = ApiClient.getClient().create(BiotechInterface.class);
        this.sessionManage = new SessionManage(context);
        this.context=context;

    }


    public LiveData<List<Datum>> getMainList() {


        if (datumMutableLiveData.getValue() == null) {
            try {
                MainList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return datumMutableLiveData;
    }

    private void MainList() throws Exception {
//        biotechInterface.getSubjectTest(sessionManage.getUserDetails().get("Subject").toString(), sessionManage.getUserDetails().get("Class").toString()).enqueue(new Callback<SubjectClass>() {
        biotechInterface.getSubjectTest("ZOLOGY", "12").enqueue(new Callback<MainList>() {
            @Override
            public void onResponse(Call<MainList> call, Response<MainList> response) {
                Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    if (!response.body().getResult().getError() && response.body().getResult().getErrorCode() == 200) {
                        datumMutableLiveData.setValue(response.body().getResult().getData());
                        ((GlobalList)context.getApplicationContext()).result = response.body().getResult();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<MainList> call, Throwable t) {

            }
        });
    }


}






















