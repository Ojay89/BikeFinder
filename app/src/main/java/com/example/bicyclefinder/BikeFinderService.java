package com.example.bicyclefinder;

import java.time.LocalDateTime;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BikeFinderService {
    @GET("bicycles")
    Call<List<Bike>> getAllBikes();


    @GET("bicycles/{bikeId}")
    Call<Bike> getBikeById(@Path("bikeId") int bikeId);

    @GET("bicycles/{foundMissing}")
    Call<List<Bike>> getFoundMissingBike(@Path("foundMissing") String foundMissing);

    @GET("bicycles/firebaseUserId/{firebaseId}")
    Call<List<Bike>> getFirebaseUserId(@Path("firebaseId") String firebaseId);


    @POST("bicycles")
    Call<Bike> saveBikeBody(@Body Bike bike);

    @DELETE("bicycles/{id}")
    Call<Integer> deleteBike(@Path("id") int id);

    @PUT("bicycles/{id}")
    Call<Bike> updateBike(@Path("id") int id, @Body Bike bike);


}


