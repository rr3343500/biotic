package com.example.bioticclasses.fragments.category;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.bioticclasses.modal.notes.DataNotes;
import com.example.bioticclasses.modal.testlist.Datum;
import com.example.bioticclasses.repositories.CategoryRepo;
import com.example.bioticclasses.repositories.HomeFragmentRep;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class CategoryViewModel extends AndroidViewModel {
    private static final String TAG = "CategoryViewModel";
    CategoryRepo CategoryRepo;
    private MutableLiveData<String> mText;

    // TODO: Implement the ViewModel

    public CategoryViewModel(@NonNull @NotNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        CategoryRepo= CategoryRepo.getInstance(application.getApplicationContext());
    }

//    public LiveData<List<Datum>> getMainList() {
//        return CategoryRepo.getMainList();
//    }

    public LiveData<List<Datum>> getCatList() {
        return CategoryRepo.getCategory();
    }
    public LiveData<List<DataNotes>> getNotesList() { return CategoryRepo.getNotes();
    }
}