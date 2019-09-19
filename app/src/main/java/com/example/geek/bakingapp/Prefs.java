package com.example.geek.bakingapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.geek.bakingapp.data.Recipe;
import com.google.gson.Gson;

public class Prefs {


    public static void saveRecipe(Context context, Recipe recipe) {
        Gson gson = new Gson();
        String recipeJsonString = gson.toJson(recipe);
        SharedPreferences sharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.widget_recipe), recipe.getName());
        editor.putString(context.getString(R.string.recipe_string), recipeJsonString);
        editor.apply();
    }

    public static Recipe loadRecipe(Context context) {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        String recipeJsonString = sharedPreferences.getString(context.getString(R.string.recipe_string), "");
        return gson.fromJson(recipeJsonString, Recipe.class);
    }
}
