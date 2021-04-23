
package com.example.bioticclasses.youtube;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class VideoList {

    @SerializedName("faq_list")
    @Expose
    private List<Faq> faqList = null;
    @SerializedName("error")
    @Expose
    private Boolean error;

    public List<Faq> getFaqList() {
        return faqList;
    }

    public void setFaqList(List<Faq> faqList) {
        this.faqList = faqList;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

}
