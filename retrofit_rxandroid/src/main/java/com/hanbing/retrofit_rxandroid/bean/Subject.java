package com.hanbing.retrofit_rxandroid.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hanbing on 2016/8/30
 */
public class Subject implements Serializable {

    public class Rating{
        float max;
        float min;
        float average;
        String stars;


        public float getAverage() {
            return average;
        }

        public void setAverage(float average) {
            this.average = average;
        }

        public float getMax() {
            return max;
        }

        public void setMax(float max) {
            this.max = max;
        }

        public float getMin() {
            return min;
        }

        public void setMin(float min) {
            this.min = min;
        }

        public String getStars() {
            return stars;
        }

        public void setStars(String stars) {
            this.stars = stars;
        }
    }


    Rating rating;

    List<String> genres;

    String title;

    List<Star> casts;

    @SerializedName("collect_count")
    int collectCount;

    @SerializedName("original_title")
    String originalTitle;

    String subtype;

    List<Star> directors;

    String year;

    Images images;

    String alt;

    String id;


    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public List<Star> getCasts() {
        return casts;
    }

    public void setCasts(List<Star> casts) {
        this.casts = casts;
    }

    public int getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(int collectCount) {
        this.collectCount = collectCount;
    }

    public List<Star> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Star> directors) {
        this.directors = directors;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
