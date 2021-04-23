
package com.example.bioticclasses.modal.attendence;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttendanceList {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("date")
    @Expose
    private String date;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
