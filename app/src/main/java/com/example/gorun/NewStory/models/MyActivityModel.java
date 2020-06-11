package com.example.gorun.NewStory.models;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class MyActivityModel  {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("distance")
    @Expose
    private String distance;

    @SerializedName("moving_time")
    @Expose
    private String movingTime;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("start_date_local")
    @Expose
    private String startDate;

    @SerializedName("average_speed")
    @Expose
    private String averageSpeed;

    @SerializedName("average_cadence")
    @Expose
    private String averageCadence;

    @SerializedName("average_heartrate")
    @Expose
    private String averageHeartrate;

    @SerializedName("elev_high")
    @Expose
    private String evelHigh;

    @SerializedName("elev_low")
    @Expose
    private String evelLow;



    public MyActivityModel(String name, String distance, String movingTime, String type, String startDate, String averageSpeed, String averageCadence, String averageHeartrate, String evelHigh, String evelLow) {
        this.name = name;
        this.distance = distance;
        this.movingTime = movingTime;
        this.type = type;
        this.startDate = startDate;
        this.averageSpeed = averageSpeed;
        this.averageCadence = averageCadence;
        this.averageHeartrate = averageHeartrate;
        this.evelHigh = evelHigh;
        this.evelLow = evelLow;
    }
   public MyActivityModel(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getMovingTime() {
        return movingTime;
    }

    public void setMovingTime(String movingTime) {
        this.movingTime = movingTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(String averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public String getAverageCadence() {
        return averageCadence;
    }

    public void setAverageCadence(String averageCadence) {
        this.averageCadence = averageCadence;
    }

    public String getAverageHeartrate() {
        return averageHeartrate;
    }

    public void setAverageHeartrate(String averageHeartrate) {
        this.averageHeartrate = averageHeartrate;
    }

    public String getEvelHigh() {
        return evelHigh;
    }

    public void setEvelHigh(String evelHigh) {
        this.evelHigh = evelHigh;
    }

    public String getEvelLow() {
        return evelLow;
    }

    public void setEvelLow(String evelLow) {
        this.evelLow = evelLow;
    }
}
