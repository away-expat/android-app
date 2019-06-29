package com.away_expat.away.dto;

import com.away_expat.away.classes.Activity;
import com.away_expat.away.classes.Event;
import com.away_expat.away.classes.Tag;
import com.away_expat.away.classes.User;

import java.util.List;

public class DetailedEventDto {

    private User creator;
    private Event event;
    private Activity activity;
    private List<User> participant;
    private List<Tag> tag;

    public DetailedEventDto() {
    }

    public DetailedEventDto(User creator, Event event, Activity activity, List<User> participant, List<Tag> tag) {
        this.creator = creator;
        this.event = event;
        this.activity = activity;
        this.participant = participant;
        this.tag = tag;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public List<User> getParticipant() {
        return participant;
    }

    public void setParticipant(List<User> participant) {
        this.participant = participant;
    }

    public List<Tag> getTag() {
        return tag;
    }

    public void setTag(List<Tag> tag) {
        this.tag = tag;
    }
}
