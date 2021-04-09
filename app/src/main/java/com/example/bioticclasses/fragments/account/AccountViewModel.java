package com.example.bioticclasses.fragments.account;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.bioticclasses.modal.subjectclass.Result;
import com.example.bioticclasses.repositories.AccountRepo;

import org.jetbrains.annotations.NotNull;

public class AccountViewModel extends AndroidViewModel {

    private static final String TAG = "AccountViewModel";
    AccountRepo myTestRepo;
    private MutableLiveData<String> mText;


    public AccountViewModel(@NonNull @NotNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        myTestRepo= AccountRepo.getInstance(application.getApplicationContext());
    }

    public LiveData<Result> getTestlist() {
        return myTestRepo.listLiveData();
    }
}