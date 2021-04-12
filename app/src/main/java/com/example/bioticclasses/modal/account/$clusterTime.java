
package com.example.bioticclasses.modal.account;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class $clusterTime {

    @SerializedName("clusterTime")
    @Expose
    private String clusterTime;
    @SerializedName("signature")
    @Expose
    private Signature signature;

    public String getClusterTime() {
        return clusterTime;
    }

    public void setClusterTime(String clusterTime) {
        this.clusterTime = clusterTime;
    }

    public Signature getSignature() {
        return signature;
    }

    public void setSignature(Signature signature) {
        this.signature = signature;
    }

}
