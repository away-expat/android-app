package com.away_expat.away.services;

import com.away_expat.away.classes.User;

import retrofit2.Call;

public class UserService {

    private RetrofitServiceGenerator retrofitServiceGenerator;

    public User createUser(User user) {
        User newUser = null;
        try {
            Call<User> call = retrofitServiceGenerator.createService(UserApiService.class).createUser(user);
            newUser = call.execute().body();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newUser;
    }

    public User updateUser(String token, Long id, User user) {
        User updatedUser = null;
        try {
            Call<User> call = retrofitServiceGenerator.createService(UserApiService.class).updateUser(token, id, user);
            updatedUser = call.execute().body();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updatedUser;
    }

    public User deleteUser(String token, Long id) {
        User deletedUser = null;
        try {
            Call<User> call = retrofitServiceGenerator.createService(UserApiService.class).deleteUser(token, id);
            deletedUser = call.execute().body();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deletedUser;
    }

    /*
    public User followUser(String token, Long connectedUserId, Long userId) {
        User followedUser = null;
        try {
            Call<User> call = retrofitServiceGenerator.createService(UserApiService.class).followUser(token, connectedUserId, userId);
            followedUser = call.execute().body();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return followedUser;
    }

    public boolean unfollowUser(String token, Long userId) {
        boolean isSuccessful = false;
        try {
            Call<User> call = retrofitServiceGenerator.createService(UserApiService.class).unfollowUser(token, userId);
            isSuccessful = call.execute().isSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccessful;
    }
    */
}
