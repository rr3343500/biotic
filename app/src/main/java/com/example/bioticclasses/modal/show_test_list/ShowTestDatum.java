
package com.example.bioticclasses.modal.show_test_list;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShowTestDatum {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("user_time_taken")
    @Expose
    private String userTimeTaken;
    @SerializedName("total_time")
    @Expose
    private String totalTime;
    @SerializedName("total_ques")
    @Expose
    private String totalQues;
    @SerializedName("total_marks")
    @Expose
    private String totalMarks;
    @SerializedName("marks_obtain")
    @Expose
    private String marksObtain;
    @SerializedName("correct_ques")
    @Expose
    private String correctQues;
    @SerializedName("wrong_ques")
    @Expose
    private String wrongQues;
    @SerializedName("leave_ques")
    @Expose
    private String leaveQues;
    @SerializedName("response")
    @Expose
    private List<Response> response = null;
    @SerializedName("test_id")
    @Expose
    private String testId;
    @SerializedName("test_name")
    @Expose
    private String testName;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;

    @SerializedName("coorect_marks")
    @Expose
    private Integer coorect_marks;

    public void setCoorect_marks(Integer coorect_marks){
        this.coorect_marks=coorect_marks;
    }

    public Integer getCoorect_marks(){
       return coorect_marks;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserTimeTaken() {
        return userTimeTaken;
    }

    public void setUserTimeTaken(String userTimeTaken) {
        this.userTimeTaken = userTimeTaken;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getTotalQues() {
        return totalQues;
    }

    public void setTotalQues(String totalQues) {
        this.totalQues = totalQues;
    }

    public String getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(String totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getMarksObtain() {
        return marksObtain;
    }

    public void setMarksObtain(String marksObtain) {
        this.marksObtain = marksObtain;
    }

    public String getCorrectQues() {
        return correctQues;
    }

    public void setCorrectQues(String correctQues) {
        this.correctQues = correctQues;
    }

    public String getWrongQues() {
        return wrongQues;
    }

    public void setWrongQues(String wrongQues) {
        this.wrongQues = wrongQues;
    }

    public String getLeaveQues() {
        return leaveQues;
    }

    public void setLeaveQues(String leaveQues) {
        this.leaveQues = leaveQues;
    }

    public List<Response> getResponse() {
        return response;
    }

    public void setResponse(List<Response> response) {
        this.response = response;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
