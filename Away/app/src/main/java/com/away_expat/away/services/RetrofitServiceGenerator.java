package com.away_expat.away.services;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceGenerator {

    private static OkHttpClient httpClient = new OkHttpClient.Builder().build();

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://51.75.122.187:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
