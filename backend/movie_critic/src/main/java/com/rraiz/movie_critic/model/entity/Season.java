package com.rraiz.movie_critic.model.entity;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Season {

    @Id
    private int id;

    @Column(nullable = true)
    private LocalDate airDate;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String name;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String overview;

    @Column(nullable = true)
    private int episodeCount;

    @Column(nullable = true)
    private String posterPath;

    @Column(nullable = true)
    private Integer seasonNumber;

    @Column(nullable = true)
    private Double voteAverage;

    @OneToMany(mappedBy = "season")
    @JsonManagedReference
    private Set<Episode> episodes;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "tv_show_id", referencedColumnName = "film_id"),
        @JoinColumn(name = "tv_show_type", referencedColumnName = "film_type")
    })
    @JsonBackReference
    private TvShow tvShow;

    @Column
    private LocalDate lastUpdated;

    public Season() {
    }

    // Parameterized Constructor
    public Season(int id, LocalDate airDate, String name, String overview, Integer episodeCount, String posterPath, Integer seasonNumber, Double voteAverage, Set<Episode> episodes, TvShow tvShow, LocalDate lastUpdated) {
        this.id = id;
        this.airDate = airDate;
        this.name = name;
        this.overview = overview;
        this.episodeCount = episodeCount;
        this.posterPath = posterPath;
        this.seasonNumber = seasonNumber;
        this.voteAverage = voteAverage;
        this.episodes = episodes;
        this.tvShow = tvShow;
        this.lastUpdated = lastUpdated;
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

    public String getOverview() {
        return overview;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Set<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Set<Episode> episodes) {
        this.episodes = episodes;
    }

    public TvShow getTvShow() {
        return tvShow;
    }

    public void setTvShow(TvShow tvShow) {
        this.tvShow = tvShow;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
