
package com.example.bioticclasses.modal.lactures;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class DataLacture {

    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("des")
    @Expose
    private String des;
    @SerializedName("stu_clas")
    @Expose
    private String stuClas;
    @SerializedName("subject_id")
    @Expose
    private String subjectId;
    @SerializedName("subject_name")
    @Expose
    private String subjectName;
    @SerializedName("vedio_link")
    @Expose
    private String vedioLink;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getStuClas() {
        return stuClas;
    }

    public void setStuClas(String stuClas) {
        this.stuClas = stuClas;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getVedioLink() {
        return vedioLink;
    }

    public void setVedioLink(String vedioLink) {
        this.vedioLink = vedioLink;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
