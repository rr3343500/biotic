package com.example.bioticclasses.global;

import android.app.Application;

import com.example.bioticclasses.modal.mytest.MyTest;
import com.example.bioticclasses.modal.testlist.Result;

import java.util.List;

public class GlobalList extends Application {


    public com.example.bioticclasses.modal.mainList.Result result;

    public List<MyTest> myTest;

    public Result testlist;

    public GlobalList() {
       result= new com.example.bioticclasses.modal.mainList.Result();
    }

    public void setResult(com.example.bioticclasses.modal.mainList.Result result){
        this.result=result;
    }

    public void setMyTest(List<MyTest> result){
        this.myTest=result;
    }

    public void setTestlist(Result testlist){
        this.testlist=testlist;
    }

}
