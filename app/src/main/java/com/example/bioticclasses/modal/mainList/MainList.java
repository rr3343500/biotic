
package com.example.bioticclasses.modal.mainList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainList {

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
