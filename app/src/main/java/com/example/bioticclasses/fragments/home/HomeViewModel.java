package com.example.bioticclasses.fragments.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bioticclasses.modal.banner.Datum;
import com.example.bioticclasses.modal.category.CatDatum;
import com.example.bioticclasses.modal.userprofile.Data;
import com.example.bioticclasses.repositories.HomeFragmentRep;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private static final String TAG = "HomeViewModel";
    HomeFragmentRep homeFragmentRep;

    private MutableLiveData<String> mText;


    public HomeViewModel(@NonNull @NotNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        homeFragmentRep= HomeFragmentRep.getInstance(application.getApplicationContext());
    }

    public LiveData<List<Datum>> getMainList() {
        return HomeFragmentRep.getMainList();
    }

    public LiveData<List<CatDatum>> getCatList() {
        return HomeFragmentRep.getCatlist();
    }

    public LiveData<Data> data() {
        return HomeFragmentRep.getData();
    }
}