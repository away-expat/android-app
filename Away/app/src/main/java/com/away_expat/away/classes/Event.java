package com.away_expat.away.classes;

import java.util.Date;

public class Event {

    private String activityName;
    private String activityDescription;
    private Date date;
    private Account creator;

    public Event(String activityName, String activityDescription, Date date, Account creator) {
        this.activityName = activityName;
        this.activityDescription = activityDescription;
        this.date = date;
        this.creator = creator;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Account getCreator() {
        return creator;
    }

    public void setCreator(Account creator) {
        this.creator = creator;
    }
}
