package com.rraiz.movie_critic.feature.content_management.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.rraiz.movie_critic.feature.film.model.entity.Movie;
import com.rraiz.movie_critic.feature.film.model.entity.TvShow;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "popular_films")
public class PopularFilms {

    @Id
    private int id;

    @ElementCollection
    @CollectionTable(name = "popular_tv_shows")
    @Column(name = "tv-show")
    private Set<TvShow> tvShows = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "popular_movies")
    @Column(name = "movie")
    private Set<Movie> movies = new HashSet<>();

    @Column(name = "last_updated")
    private LocalDate lastUpdated;

    public PopularFilms() {
    }

    public PopularFilms(Set<TvShow> tvShows, Set<Movie> movies, LocalDate lastUpdated) {
        this.tvShows = tvShows;
        this.movies = movies;
        this.lastUpdated = lastUpdated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<TvShow> getTvShows() {
        return tvShows;
    }

    public void setTvShows(Set<TvShow> tvShows) {
        this.tvShows = tvShows;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
