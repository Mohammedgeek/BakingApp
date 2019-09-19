package com.example.geek.bakingapp.data.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static  RecipesService recipesService;

    public static RecipesService getRecipesService() {
        if(recipesService != null)
            return recipesService;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        recipesService = retrofit.create(RecipesService.class);
        return recipesService;
    }

    private RetrofitClient() {
    }
}
