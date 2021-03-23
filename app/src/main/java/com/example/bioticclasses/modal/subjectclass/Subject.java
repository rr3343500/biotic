
package com.example.bioticclasses.modal.subjectclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subject {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name_en")
    @Expose
    private String nameEn;
    @SerializedName("stu_class")
    @Expose
    private String stuClass;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getStuClass() {
        return stuClass;
    }

    public void setStuClass(String stuClass) {
        this.stuClass = stuClass;
    }

}
