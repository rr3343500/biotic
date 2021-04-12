package com.example.bioticclasses.fragments.mytest;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bioticclasses.modal.show_test_list.ShowTestDatum;
import com.example.bioticclasses.repositories.MyTestRepo;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MyTestViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private static final String TAG = "MyTestViewModel";
    MyTestRepo myTestRepo;
    private MutableLiveData<String> mText;


    public MyTestViewModel(@NonNull @NotNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        myTestRepo= MyTestRepo.getInstance(application.getApplicationContext());
    }

//    public LiveData<List<Datum>> getMainList() {
//        return CategoryRepo.getMainList();
//    }

    public LiveData<List<ShowTestDatum>> getTestlist() {
        return myTestRepo.listLiveData();
    }
}