
package com.example.bioticclasses.youtube;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Faq {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("heading")
    @Expose
    private String heading;
    @SerializedName("des")
    @Expose
    private String des;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("time")
    @Expose
    private String time;

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

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
