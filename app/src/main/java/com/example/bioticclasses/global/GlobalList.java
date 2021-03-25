package com.example.bioticclasses.global;

import android.app.Application;

import com.example.bioticclasses.modal.mainList.Result;

public class GlobalList extends Application {


    public Result result;

    public GlobalList() {
       result= new Result();
    }

    public void setResult(Result result){
        this.result=result;
    }

}
