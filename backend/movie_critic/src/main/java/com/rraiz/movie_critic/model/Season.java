package com.rraiz.movie_critic.model;

import java.time.LocalDate;
import java.util.Set;

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

    @Column(nullable = true, name = "air_date")
    private LocalDate airDate;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String overview;

    @Column(nullable = true, name = "poster_path")
    private String posterPath;

    @Column(nullable = true, name = "season_number")
    private Integer seasonNumber;

    @Column(nullable = true, name = "vote_count")
    private  Integer voteCount;

    @Column(nullable = true, name = "vote_average")
    private Double voteAverage;

    @OneToMany(mappedBy = "season")
    private Set<Episode> episodes;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "tv_show_id", referencedColumnName = "film_id"),
        @JoinColumn(name = "tv_show_type", referencedColumnName = "film_type")
    })
    private TvShow tvShow;

    // Parameterized Constructor
    public Season(int id, LocalDate airDate, String overview, String posterPath, Integer seasonNumber, Integer voteCount, Double voteAverage, Set<Episode> episodes, TvShow tvShow) {
        this.id = id;
        this.airDate = airDate;
        this.overview = overview;
        this.posterPath = posterPath;
        this.seasonNumber = seasonNumber;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.episodes = episodes;
        this.tvShow = tvShow;
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

    public String getOverview() {
        return overview;
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

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
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
}
