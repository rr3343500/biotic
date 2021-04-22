package com.example.bioticclasses.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.bioticclasses.Service.ApiClient;
import com.example.bioticclasses.Service.BiotechInterface;
import com.example.bioticclasses.modal.show_test_list.ShowTestDatum;
import com.example.bioticclasses.modal.show_test_list.TestShowList;
import com.example.bioticclasses.other.SessionManage;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTestRepo {

    public  MutableLiveData<List<ShowTestDatum>>listMutableLiveData= new MutableLiveData<>();
    static BiotechInterface biotechInterface;
    SessionManage sessionManage;
    Context context;
    public static MyTestRepo ourInstance;
    private  final String TAG = "MyTestRepo";


    public static MyTestRepo getInstance(Context context) {
        return ourInstance = new MyTestRepo(context);
    }


    public MyTestRepo(Context context) {
        this.biotechInterface = ApiClient.getClient().create(BiotechInterface.class);
        this.sessionManage = new SessionManage(context);
        this.context=context;

    }


    public LiveData<List<ShowTestDatum>> listLiveData() {

        if (listMutableLiveData.getValue() == null) {
            try {
                biotechInterface.getTestList(sessionManage.getUserDetails().get("userid") , sessionManage.getUserDetails().get("Class")).enqueue(new Callback<TestShowList>() {
                    @Override
                    public void onResponse(Call<TestShowList> call, Response<TestShowList> response) {
                        if(response.isSuccessful()){
                            listMutableLiveData.postValue(response.body().getResult().getData());
                        }

                    }

                    @Override
                    public void onFailure(Call<TestShowList> call, Throwable t) {

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listMutableLiveData;
    }


}
