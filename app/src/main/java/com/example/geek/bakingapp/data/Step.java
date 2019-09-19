package com.example.geek.bakingapp.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Step {
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("shortDescription")
    @Expose
    public String shortDescription;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("videoURL")
    @Expose
    public String videoURL;
    @SerializedName("thumbnailURL")
    @Expose
    public String thumbnailURL;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }
}
