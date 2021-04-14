
package com.example.bioticclasses.modal.userprofile;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class StuSub {

    @SerializedName("sid")
    @Expose
    private String sid;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("subj")
    @Expose
    private String subj;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    public String get_id() {
        return sid;
    }

    public void set_id(String sid) {
        this.sid = sid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubj() {
        return subj;
    }

    public void setSubj(String subj) {
        this.subj = subj;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
