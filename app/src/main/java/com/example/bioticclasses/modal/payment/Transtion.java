
package com.example.bioticclasses.modal.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Transtion {

    @SerializedName("student_id")
    @Expose
    private String studentId;
    @SerializedName("class_id")
    @Expose
    private String classId;
    @SerializedName("student_name")
    @Expose
    private String studentName;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("transition_id")
    @Expose
    private Integer transitionId;
    @SerializedName("order_id")
    @Expose
    private String orderId;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getTransitionId() {
        return transitionId;
    }

    public void setTransitionId(Integer transitionId) {
        this.transitionId = transitionId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
