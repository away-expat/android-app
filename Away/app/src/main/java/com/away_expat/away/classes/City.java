package com.away_expat.away.classes;

public class City {

    private int id;
    private String name;
    private String country;
    private String place_id;
    private String location;

    public City(int id, String name, String country, String place_id, String location) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.place_id = place_id;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getPlace_id() {
        return place_id;
    }

    public String getLocation() {
        return location;
    }
}
