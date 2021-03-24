package com.example.bioticclasses.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bioticclasses.modal.mainList.Datum;
import com.example.bioticclasses.repositories.MainActivityRep;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {


    private static final String TAG = "MainActivityViewModel";
    MainActivityRep mainActivityRep;


    public MainActivityViewModel(@NonNull @NotNull Application application) {
        super(application);
        mainActivityRep = MainActivityRep.getInstance(application.getApplicationContext());

    }


    public LiveData<List<Datum>> getMainList() {
        return mainActivityRep.getMainList();
    }

}

