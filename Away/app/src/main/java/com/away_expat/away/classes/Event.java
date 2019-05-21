package com.away_expat.away.classes;

import java.util.Date;
import java.util.List;

public class Event {

    private String name;
    private String description;
    private Date date;
    private User creator;
    private Activity activity;
    private List<User> participant;

    public Event(String name, String description, Date date, User creator, Activity activity, List<User> participant) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.creator = creator;
        this.activity = activity;
        this.participant = participant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
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
