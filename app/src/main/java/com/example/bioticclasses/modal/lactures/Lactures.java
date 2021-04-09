
package com.example.bioticclasses.modal.lactures;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Lactures {

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
