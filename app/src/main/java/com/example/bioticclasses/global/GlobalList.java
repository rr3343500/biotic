package com.example.bioticclasses.global;

import android.app.Application;

import com.example.bioticclasses.modal.mainList.Result;
import com.example.bioticclasses.modal.mytest.MyTest;

import java.util.List;

public class GlobalList extends Application {


    public Result result;

    public List<MyTest> myTest;

    public GlobalList() {
       result= new Result();
    }

    public void setResult(Result result){
        this.result=result;
    }

    public void setMyTest(List<MyTest> result){
        this.myTest=result;
    }

}
