package com.away_expat.away.services;

import com.away_expat.away.classes.Event;
import com.away_expat.away.dto.DetailedEventDto;
import com.away_expat.away.dto.ParticipateDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EventApiService {

    @POST("/events/")
    Call<DetailedEventDto> createEvent(@Header("Authorization") String token, @Body Event event);

    @GET("/events/recherche/{search}")
    Call<List<Event>> searchByText(@Header("Authorization") String token, @Path("search") String search);

    @GET("/events/getEventWithDetails/{id}")
    Call<DetailedEventDto> getById(@Header("Authorization") String token, @Path("id") int id);

    @POST("/events/postParticipateAtEvent")
    Call<DetailedEventDto> participate(@Header("Authorization") String token, @Body ParticipateDto id);

    @HTTP(method = "DELETE", path = "/events/deleteParticipationAtEvent", hasBody = true)
    Call<DetailedEventDto> quit(@Header("Authorization") String token, @Body ParticipateDto id);
}
