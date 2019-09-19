package com.example.geek.bakingapp.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

import androidx.annotation.Nullable;

@Parcel
public class Recipe {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("ingredients")
    @Expose
    public List<Ingredient> ingredients = null;
    @SerializedName("steps")
    @Expose
    public List<Step> steps = null;
    @SerializedName("servings")
    @Expose
    public int servings;
    @SerializedName("image")
    @Expose
    public String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(@Nullable Object obj) {

        if(!(obj instanceof Recipe))
            return super.equals(obj);

        Recipe other = (Recipe) obj;

        return this.getId() == other.getId() && this.image.equals(other.getImage()) && this.getName().equals(other.getName());
    }
}