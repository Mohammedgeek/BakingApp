package com.example.geek.bakingapp.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Ingredient {

    @SerializedName("quantity")
    @Expose
    public double quantity;
    @SerializedName("measure")
    @Expose
    public String measure;
    @SerializedName("ingredient")
    @Expose
    public String ingredient;

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

}