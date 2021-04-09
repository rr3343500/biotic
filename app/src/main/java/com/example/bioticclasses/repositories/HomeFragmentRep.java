package com.example.bioticclasses.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bioticclasses.Service.ApiClient;
import com.example.bioticclasses.Service.BiotechInterface;
import com.example.bioticclasses.modal.banner.Banner;
import com.example.bioticclasses.modal.banner.Datum;
import com.example.bioticclasses.modal.category.CatDatum;
import com.example.bioticclasses.modal.category.Category;
import com.example.bioticclasses.other.SessionManage;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragmentRep {

    public static MutableLiveData<List<Datum>> listMutableLiveData = new MutableLiveData<List<Datum>>();
    public static MutableLiveData<List<CatDatum>> catListMutableLiveData = new MutableLiveData<List<CatDatum>>();
    static BiotechInterface biotechInterface;
    SessionManage sessionManage;
    Context context;
    public static HomeFragmentRep ourInstance;
    private static final String TAG = "HomeFragmentRep";





    public static HomeFragmentRep getInstance(Context context) {
        return ourInstance = new HomeFragmentRep(context);
    }

    public HomeFragmentRep(Context context) {
        this.biotechInterface = ApiClient.getClient().create(BiotechInterface.class);
        this.sessionManage = new SessionManage(context);
        this.context=context;

    }


    public static LiveData<List<Datum>> getMainList() {


        if (listMutableLiveData.getValue() == null) {
            try {
               setLiveData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listMutableLiveData;
    }


    public static LiveData<List<CatDatum>> getCatlist() {


        if (catListMutableLiveData.getValue() == null) {
            try {
                setLiveData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return catListMutableLiveData;
    }



    private static void setLiveData(){
        biotechInterface = ApiClient.getClient().create(BiotechInterface.class);
        biotechInterface.BANNER_CALL().enqueue(new Callback<Banner>() {
            @Override
            public void onResponse(Call<Banner> call, Response<Banner> response) {
                if (response.isSuccessful()){
                    if (!response.body().getResult().getError() && response.body().getResult().getErrorCode() == 200) {
                        listMutableLiveData.setValue(response.body().getResult().getData());

                    }
                }
            }

            @Override
            public void onFailure(Call<Banner> call, Throwable t) {

            }
        });

        biotechInterface.CATEGORY_CALL().enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful()){
                    if (!response.body().getResult().getError() && response.body().getResult().getErrorCode() == 200) {
                     catListMutableLiveData.setValue(response.body().getResult().getData());

                    }
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {

            }
        });
    }
}
