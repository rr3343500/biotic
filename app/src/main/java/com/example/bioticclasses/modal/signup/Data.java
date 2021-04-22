
package com.example.bioticclasses.modal.signup;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("total_amount")
    @Expose
    private Integer totalAmount;
    @SerializedName("pending_amount")
    @Expose
    private Integer pendingAmount;
    @SerializedName("paid_amount")
    @Expose
    private Integer paidAmount;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("transtion")
    @Expose
    private List<Object> transtion = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name_en")
    @Expose
    private String nameEn;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("stu_clas")
    @Expose
    private String stuClas;
    @SerializedName("medium")
    @Expose
    private String medium;
    @SerializedName("img_name")
    @Expose
    private String imgName;
    @SerializedName("img_path")
    @Expose
    private String imgPath;
    @SerializedName("father_name")
    @Expose
    private String fatherName;
    @SerializedName("parents_mobile")
    @Expose
    private String parentsMobile;
    @SerializedName("parents_email")
    @Expose
    private String parentsEmail;
    @SerializedName("user_token")
    @Expose
    private String userToken;
    @SerializedName("stu_sub")
    @Expose
    private List<StuSub> stuSub = null;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("gender")
    @Expose
    private String gender;

    public void setGender(String gender){
        this.gender=gender;
    }

    public String getGender(){
        return gender;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getPendingAmount() {
        return pendingAmount;
    }

    public void setPendingAmount(Integer pendingAmount) {
        this.pendingAmount = pendingAmount;
    }

    public Integer getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Integer paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public List<Object> getTranstion() {
        return transtion;
    }

    public void setTranstion(List<Object> transtion) {
        this.transtion = transtion;
    }

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStuClas() {
        return stuClas;
    }

    public void setStuClas(String stuClas) {
        this.stuClas = stuClas;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getParentsMobile() {
        return parentsMobile;
    }

    public void setParentsMobile(String parentsMobile) {
        this.parentsMobile = parentsMobile;
    }

    public String getParentsEmail() {
        return parentsEmail;
    }

    public void setParentsEmail(String parentsEmail) {
        this.parentsEmail = parentsEmail;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public List<StuSub> getStuSub() {
        return stuSub;
    }

    public void setStuSub(List<StuSub> stuSub) {
        this.stuSub = stuSub;
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
