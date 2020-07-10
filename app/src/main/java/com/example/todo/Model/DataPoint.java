package com.example.todo.Model;

public class DataPoint {

   String Title;
   Long TotalHour;

    public DataPoint() {
    }

    public DataPoint(String title, Long totalHour) {
        Title = title;
        TotalHour = totalHour;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Long getTotalHour() {
        return TotalHour;
    }

    public void setTotalHour(Long totalHour) {
        TotalHour = totalHour;
    }
}
