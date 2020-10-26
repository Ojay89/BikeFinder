package com.example.bicyclefinder;

public class ApiUtils {
    private ApiUtils() {
    }

    private static final String BASE_URL = "https://anbo-bicyclefinderdb.azurewebsites.net/api/";

    public static BikeFinderService getBikeFinderService() {

        return RetrofitClient.getClient(BASE_URL).create(BikeFinderService.class);
    }
}

