package com.away_expat.away.classes;

import com.google.gson.annotations.SerializedName;

public class Tag {

    @SerializedName("name")
    private String name;

    //TO REMOVE
    public Tag(String id, String name) {
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
