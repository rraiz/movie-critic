package com.rraiz.movie_critic.model;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String tconst;
    private String titleType;
    private String title;
    private int startYear;
    private int endYear;
    private int runtimeMinutes;
    @ElementCollection
    private List<String> genres;
    private int averageRating;
    private int numVotes;
    private String coverURL;

    public Media(String tconst, String titleType, String title, int startYear, int endYear, int runtimeMinutes, List<String> genres, int averageRating, int numVotes, String coverURL) 
    {
        this.tconst = tconst;
        this.titleType = titleType;
        this.title = title;
        this.startYear = startYear;
        this.endYear = endYear;
        this.runtimeMinutes = runtimeMinutes;
        this.genres = genres;
        this.averageRating = averageRating;
        this.numVotes = numVotes;
        this.coverURL = coverURL;
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTconst() {
        return tconst;
    }
    
    public void setTconst(String tconst) {
        this.tconst = tconst;
    }
    
    public String getTitleType() {
        return titleType;
    }
    
    public void setTitleType(String titleType) {
        this.titleType = titleType;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public int getStartYear() {
        return startYear;
    }
    
    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }
    
    public int getEndYear() {
        return endYear;
    }
    
    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }
    
    public int getRuntimeMinutes() {
        return runtimeMinutes;
    }
    
    public void setRuntimeMinutes(int runtimeMinutes) {
        this.runtimeMinutes = runtimeMinutes;
    }
    
    public List<String> getGenres() {
        return genres;
    }
    
    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
    
    public int getAverageRating() {
        return averageRating;
    }
    
    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }
    
    public int getNumVotes() {
        return numVotes;
    }
    
    public void setNumVotes(int numVotes) {
        this.numVotes = numVotes;
    }
    
    public String getCoverURL() {
        return coverURL;
    }
    
    public void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
    }

}
