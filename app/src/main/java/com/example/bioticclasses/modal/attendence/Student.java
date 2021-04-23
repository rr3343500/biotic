
package com.example.bioticclasses.modal.attendence;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Student {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("student_id")
    @Expose
    private String studentId;
    @SerializedName("name_en")
    @Expose
    private String nameEn;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
