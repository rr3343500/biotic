
package com.example.bioticclasses.modal.mainList;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Test {

    @SerializedName("time_limit")
    @Expose
    private String timeLimit;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("heading")
    @Expose
    private String heading;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("stu_clas")
    @Expose
    private String stuClas;
    @SerializedName("wrong_marks")
    @Expose
    private Integer wrongMarks;
    @SerializedName("coorect_marks")
    @Expose
    private Integer coorectMarks;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("total_question")
    @Expose
    private Integer totalQuestion;
    @SerializedName("questions")
    @Expose
    private List<Question> questions = null;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

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

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStuClas() {
        return stuClas;
    }

    public void setStuClas(String stuClas) {
        this.stuClas = stuClas;
    }

    public Integer getWrongMarks() {
        return wrongMarks;
    }

    public void setWrongMarks(Integer wrongMarks) {
        this.wrongMarks = wrongMarks;
    }

    public Integer getCoorectMarks() {
        return coorectMarks;
    }

    public void setCoorectMarks(Integer coorectMarks) {
        this.coorectMarks = coorectMarks;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(Integer totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
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
