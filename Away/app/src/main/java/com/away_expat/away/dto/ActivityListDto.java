package com.away_expat.away.dto;

import com.away_expat.away.classes.Activity;

import java.util.List;

public class ActivityListDto {

    private String token;
    private List<Activity> results;

    public ActivityListDto(String token, List<Activity> results) {
        this.token = token;
        this.results = results;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Activity> getResults() {
        return results;
    }

    public void setResults(List<Activity> results) {
        this.results = results;
    }
}
