
package com.example.bioticclasses.modal.account;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Signature {

    @SerializedName("hash")
    @Expose
    private String hash;
    @SerializedName("keyId")
    @Expose
    private String keyId;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

}
