
package com.example.bioticclasses.modal.mainList;

import android.app.Application;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum  {

    @SerializedName("tests")
    @Expose
    private List<Test> tests = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name_en")
    @Expose
    private String nameEn;

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

}
