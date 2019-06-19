package com.away_expat.away.classes;

import com.google.gson.annotations.SerializedName;

public class Tag {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    //TO REMOVE
    public Tag(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Tag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
