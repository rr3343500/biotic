
package com.example.bioticclasses.modal.account;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Data {

    @SerializedName("n")
    @Expose
    private Integer n;
    @SerializedName("nModified")
    @Expose
    private Integer nModified;
    @SerializedName("opTime")
    @Expose
    private OpTime opTime;
    @SerializedName("electionId")
    @Expose
    private String electionId;
    @SerializedName("ok")
    @Expose
    private Integer ok;
    @SerializedName("$clusterTime")
    @Expose
    private $clusterTime $clusterTime;
    @SerializedName("operationTime")
    @Expose
    private String operationTime;

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public Integer getnModified() {
        return nModified;
    }

    public void setnModified(Integer nModified) {
        this.nModified = nModified;
    }

    public OpTime getOpTime() {
        return opTime;
    }

    public void setOpTime(OpTime opTime) {
        this.opTime = opTime;
    }

    public String getElectionId() {
        return electionId;
    }

    public void setElectionId(String electionId) {
        this.electionId = electionId;
    }

    public Integer getOk() {
        return ok;
    }

    public void setOk(Integer ok) {
        this.ok = ok;
    }

    public $clusterTime get$clusterTime() {
        return $clusterTime;
    }

    public void set$clusterTime($clusterTime $clusterTime) {
        this.$clusterTime = $clusterTime;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
    }

}
