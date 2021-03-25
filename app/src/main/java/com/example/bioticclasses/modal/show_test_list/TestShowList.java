
package com.example.bioticclasses.modal.show_test_list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestShowList {

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
