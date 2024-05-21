package com.rraiz.movie_critic.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "episode")
public class Episode {

    @Id
    private int id;

    @Column(nullable = true, name = "air_date")
    private LocalDate airDate;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String name;

    @Column(nullable = true, name = "episode_number")
    private Integer episodeNumber;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String overview;

    @Column(nullable = true)
    private Integer runtime;

    @Column(nullable = true, name = "still_path")
    private String stillPath;

    @Column(nullable = true, name = "vote_average")
    private Double voteAverage;

    @Column(nullable = true, name = "vote_count")
    private Integer voteCount;

    @ManyToOne
    @JoinColumn(name = "season_id", nullable = false)
    private Season season;

    // Default Constructor
    public Episode() {
    }

    // Parameterized Constructor
    public Episode(LocalDate airDate, String name, Integer episodeNumber, String overview, Integer runtime, String stillPath, Double voteAverage, Integer voteCount, Season season) {
        this.airDate = airDate;
        this.name = name;
        this.episodeNumber = episodeNumber;
        this.overview = overview;
        this.runtime = runtime;
        this.stillPath = stillPath;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.season = season;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getAirDate() {
        return airDate;
    }

    public void setAirDate(LocalDate airDate) {
        this.airDate = airDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getStillPath() {
        return stillPath;
    }

    public void setStillPath(String stillPath) {
        this.stillPath = stillPath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }
}
