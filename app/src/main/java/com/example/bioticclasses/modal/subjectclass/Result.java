
package com.example.bioticclasses.modal.subjectclass;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("class")
    @Expose
    private List<Clas> _class = null;
    @SerializedName("subject")
    @Expose
    private List<Subject> subject = null;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("error_code")
    @Expose
    private Integer errorCode;
    @SerializedName("message")
    @Expose
    private String message;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public List<Clas> getClass_() {
        return _class;
    }

    public void setClass_(List<Clas> _class) {
        this._class = _class;
    }

    public List<Subject> getSubject() {
        return subject;
    }

    public void setSubject(List<Subject> subject) {
        this.subject = subject;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
