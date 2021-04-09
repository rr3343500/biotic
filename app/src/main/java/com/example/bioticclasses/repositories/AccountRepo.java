package com.example.bioticclasses.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bioticclasses.Service.ApiClient;
import com.example.bioticclasses.Service.BiotechInterface;
import com.example.bioticclasses.modal.subjectclass.Result;
import com.example.bioticclasses.modal.subjectclass.SubjectClass;
import com.example.bioticclasses.other.SessionManage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountRepo {

    public MutableLiveData<Result> listMutableLiveData= new MutableLiveData<Result>();
    static BiotechInterface biotechInterface;
    SessionManage sessionManage;
    Context context;
    public static AccountRepo ourInstance;
    private  final String TAG = "AccountRepo";


    public static AccountRepo getInstance(Context context) {
        return ourInstance = new AccountRepo(context);
    }


    public AccountRepo(Context context) {
        this.biotechInterface = ApiClient.getClient().create(BiotechInterface.class);
        this.sessionManage = new SessionManage(context);
        this.context=context;

    }


    public LiveData<Result> listLiveData() {

        if (listMutableLiveData.getValue() == null) {
            try {
                biotechInterface.Fetch_sub_class("").enqueue(new Callback<SubjectClass>() {
                    @Override
                    public void onResponse(Call<SubjectClass> call, Response<SubjectClass> response) {
                        if (response.isSuccessful()){
                            if (!response.body().getResult().getError()  && response.body().getResult().getErrorCode()==200){
                            listMutableLiveData.setValue(response.body().getResult());
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<SubjectClass> call, Throwable t) {

                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listMutableLiveData;
    }


}
