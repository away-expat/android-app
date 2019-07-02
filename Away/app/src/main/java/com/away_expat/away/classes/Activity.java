package com.away_expat.away.classes;

import java.util.ArrayList;
import java.util.List;

public class Activity {

    private Integer id;
    private String name;
    private String address;
    private Float rating;
    private String url;
    private String photos;
    private List<String> type;

    public Activity() {

    }

    public Activity(String name, String address) {
        this.name = name;
        this.address = address;
        this.type = new ArrayList<>();
    }

    public Activity(String name, String address, Float rating, String url, String photos, List<String> type) {
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.url = url;
        this.photos = photos;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Float getRating() {
        return rating;
    }

    public String getUrl() {
        return url;
    }

    public String getPhotos() {
        return photos;
    }

    public List<String> getType() {
        return type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
