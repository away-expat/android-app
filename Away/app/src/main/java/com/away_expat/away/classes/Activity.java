package com.away_expat.away.classes;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Activity {

    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("address")
    private String address;
    @SerializedName("rating")
    private Float rating;
    @SerializedName("url")
    private String url;
    @SerializedName("photos")
    private String photos;
    @SerializedName("type")
    private List<String> type;

    public Activity() {

    }

    public Activity(String name, String address) {
        this.name = name;
        this.address = address;
        this.type = new ArrayList<>();
        //this.type.add(new Tag("","FAKE"));
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

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", rating=" + rating +
                ", url='" + url + '\'' +
                ", photos='" + photos + '\'' +
                ", type=" + type +
                '}';
    }
}
