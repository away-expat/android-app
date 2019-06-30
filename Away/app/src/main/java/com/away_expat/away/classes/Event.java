package com.away_expat.away.classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event {

    private Integer id;
    private String title;
    private String description;
    private String hour;
    private String date;
    private Integer idActivity;

    public Event() {
    }

    public Event(int id, String title, String description, String hour, String date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.hour = hour;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIdActivity() {
        return idActivity;
    }

    public void setIdActivity(int idActivity) {
        this.idActivity = idActivity;
    }

    public List<String> isComplete() {
        List<String> msg = new ArrayList<>();
        if (title.equals("")) {
            msg.add("title");
        }
        if (description.equals("")) {
            msg.add("description");
        }
        if (hour == null) {
            msg.add("hour");
        }
        if (date == null) {
            msg.add("date");
        }
        if (idActivity == null) {
            msg.add("activity");
        }
        return msg;
    }
}
