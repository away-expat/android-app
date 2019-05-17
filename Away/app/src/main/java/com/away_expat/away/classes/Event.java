package com.away_expat.away.classes;

import java.util.Date;
import java.util.List;

public class Event {

    private String activityName;
    private String activityDescription;
    private Date date;
    private User creator;
    private List<User> participant;

    public Event(String activityName, String activityDescription, Date date, User creator, List<User> participant) {
        this.activityName = activityName;
        this.activityDescription = activityDescription;
        this.date = date;
        this.creator = creator;
        this.participant = participant;
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

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<User> getParticipant() {
        return participant;
    }

    public void setParticipant(List<User> participant) {
        this.participant = participant;
    }
}
