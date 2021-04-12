package com.example.bioticclasses.repositories;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bioticclasses.Service.ApiClient;
import com.example.bioticclasses.Service.BiotechInterface;

import com.example.bioticclasses.global.GlobalList;
import com.example.bioticclasses.modal.notes.DataNotes;
import com.example.bioticclasses.modal.notes.Notes;
import com.example.bioticclasses.modal.testlist.Datum;
import com.example.bioticclasses.modal.testlist.Result;
import com.example.bioticclasses.modal.testlist.TestList;
import com.example.bioticclasses.other.SessionManage;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepo {

    public static MutableLiveData<List<Datum>> listMutableLiveData = new MutableLiveData<List<Datum>>();
    public static MutableLiveData<List<DataNotes>> noteslist = new MutableLiveData<List<DataNotes>>();

    static BiotechInterface biotechInterface;
    SessionManage sessionManage;
    Context context;
    public static CategoryRepo ourInstance;
    private static final String TAG = "CategoryRepo";



    public static CategoryRepo getInstance(Context context) {
        return ourInstance = new CategoryRepo(context);
    }

    public CategoryRepo(Context context) {
        this.biotechInterface = ApiClient.getClient().create(BiotechInterface.class);
        this.sessionManage = new SessionManage(context);
        this.context=context;

    }


    public  LiveData<List<Datum>> getCategory(String cat) {

        sessionManage.getUserDetails().get("Subject");
            try {
                sessionManage.getUserDetails().get("Subject");
                setLiveData(cat);
            } catch (Exception e) {
                e.printStackTrace();
            }
        return listMutableLiveData;
    }


    public  LiveData<List<DataNotes>> getNotes() {

        sessionManage.getUserDetails().get("Subject");
        try {
            sessionManage.getUserDetails().get("Subject");
            setNotesData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return noteslist;
    }


    private  void setLiveData(String cat) {
        try {
            JSONObject jsonObject= new JSONObject(sessionManage.getUserDetails().get("CurrentSubject")) ;
            Iterator<?> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = String.valueOf(keys.next());
                JsonObject finalsubject= new JsonObject();
//                finalsubject.addProperty("subject_id","606daed1aa5f48522a2170cb");
                finalsubject.addProperty("subject_id",key);
                finalsubject.addProperty("cat_name",cat);
                biotechInterface.CATEGORYTestListCall(finalsubject).enqueue(new Callback<TestList>() {
                    @Override
                    public void onResponse(Call<TestList> call, Response<TestList> response) {
                        if(response.isSuccessful()){
                            if (!response.body().getResult().getError()  && response.body().getResult().getErrorCode()==200){
                                Log.e(TAG, "onResponse: " + response.body().getResult().getData().size() );
                                listMutableLiveData.postValue(response.body().getResult().getData());
                                ((GlobalList)context.getApplicationContext()).setTestlist(response.body().getResult());
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<TestList> call, Throwable t) {
                        Log.e("afsdfds",t.getMessage());
                    }
                });

                break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private  void setNotesData() {
        try {
            JSONObject jsonObject= new JSONObject(sessionManage.getUserDetails().get("CurrentSubject")) ;
            Iterator<?> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = String.valueOf(keys.next());
                JsonObject finalsubject= new JsonObject();
//                finalsubject.addProperty("subject_id","606daed1aa5f48522a2170cb");
                finalsubject.addProperty("subject_id",key);
                finalsubject.addProperty("stu_clas",sessionManage.getUserDetails().get("Class"));
                Log.e("dfsfsf",sessionManage.getUserDetails().get("Class"));
                biotechInterface.NOTES_CALL(finalsubject).enqueue(new Callback<Notes>() {
                    @Override
                    public void onResponse(Call<Notes> call, Response<Notes> response) {
                        if(response.isSuccessful()){
                            if (!response.body().getResult().getError()  && response.body().getResult().getErrorCode()==200){
                                Log.e(TAG, "onResponse: " + response.body().getResult().getData().size() );
                                noteslist.postValue(response.body().getResult().getData());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Notes> call, Throwable t) {

                    }
                });





                break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
