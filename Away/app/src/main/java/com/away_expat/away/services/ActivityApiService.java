package com.away_expat.away.services;

import com.away_expat.away.classes.Activity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ActivityApiService {

    @GET("/activities/googleByCity/{city}/{tag}")
    Call<List<Activity>> getActivitiesByTag(@Path("city") String city, @Path("tag") String tag);
}
