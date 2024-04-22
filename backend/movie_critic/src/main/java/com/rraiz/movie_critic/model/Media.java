package com.rraiz.movie_critic.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Media {

    @Id
    private int tconst;
    private String titleType;
    private String title;

    @Column(nullable = true)
    private Integer startYear;

    @Column(nullable = true)
    private Integer endYear;

    @Column(nullable = true)
    private Integer runtimeMinutes;

    @ElementCollection
    @Column(nullable = true, columnDefinition = "TEXT")
    private List<String> genres;

    @Column(nullable = true)
    private Double averageRating;

    @Column(nullable = true)
    private Integer numVotes;
    
    @Column(nullable = true, columnDefinition = "TEXT")
    private String coverURL;

    public Media() {
        }

    public Media(int tconst, String titleType, String title, Integer startYear, Integer endYear, Integer runtimeMinutes, List<String> genres, Double averageRating, Integer numVotes, String coverURL) 
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
    
    public int getTconst() {
        return tconst;
    }
    
    public void setTconst(int tconst) {
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
    
    public Integer getStartYear() {
        return startYear;
    }
    
    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }
    
    public Integer getEndYear() {
        return endYear;
    }
    
    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }
    
    public Integer getRuntimeMinutes() {
        return runtimeMinutes;
    }
    
    public void setRuntimeMinutes(Integer runtimeMinutes) {
        this.runtimeMinutes = runtimeMinutes;
    }
    
    public List<String> getGenres() {
        return genres;
    }
    
    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
    
    public Double getAverageRating() {
        return averageRating;
    }
    
    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
    
    public Integer getNumVotes() {
        return numVotes;
    }
    
    public void setNumVotes(Integer numVotes) {
        this.numVotes = numVotes;
    }
    
    public String getCoverURL() {
        return coverURL;
    }
    
    public void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
    }

}
