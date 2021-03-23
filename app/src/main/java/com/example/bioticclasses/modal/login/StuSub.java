
package com.example.bioticclasses.modal.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StuSub {

    @SerializedName("name")
    @Expose
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
