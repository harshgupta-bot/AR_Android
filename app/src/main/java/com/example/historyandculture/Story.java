package com.example.historyandculture;

public class Story {
    private int imageResId;
    private String title;
    private String year;
    private String location;
    private String description;

    public Story(String title, String year, String location, String description,int imageResId) {
        this.imageResId = imageResId;
        this.title = title;
        this.year = year;
        this.location = location;
        this.description = description;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }
}
