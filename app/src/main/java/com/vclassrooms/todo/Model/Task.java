package com.vclassrooms.todo.Model;

public class Task {
    private String Title;
    private String SubPoints;
    private String Date;
    private String FromTime;
    private String ToTime;


    public Task() {
    }

    public Task(String title, String subPoints, String date,String fromTime, String toTime) {
        Title = title;
        SubPoints = subPoints;
        Date = date;
        FromTime = fromTime;
        ToTime = toTime;
    }

    public String getFromTime() {
        return FromTime;
    }

    public void setFromTime(String fromTime) {
        FromTime = fromTime;
    }

    public String getToTime() {
        return ToTime;
    }

    public void setToTime(String toTime) {
        ToTime = toTime;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getSubPoints() {
        return SubPoints;
    }

    public void setSubPoints(String subPoints) {
        SubPoints = subPoints;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }


}
