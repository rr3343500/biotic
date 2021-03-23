
package com.example.bioticclasses.modal.subjectclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubjectClass {

    @SerializedName("result")
    @Expose
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

}
