package com.rraiz.movie_critic.feature.content_management.dto.PopularFilms;

import java.time.LocalDate;

import com.rraiz.movie_critic.feature.film.model.entity.Film;
import com.rraiz.movie_critic.feature.film.model.entity.Movie;
import com.rraiz.movie_critic.feature.film.model.entity.TvShow;

public class FilmDTO {

    private int id;
    private String title;
    private String posterPath;
    private double popularity;
    private int voteCount;
    private double voteAverage;
    private LocalDate releaseDate;


    // Constructor for Movie
    public FilmDTO(Movie movie) {
        setCommonFields((Film) movie);
        this.releaseDate = movie.getReleaseDate();
    }

    // Constructor for TV Show
    public FilmDTO(TvShow tvShow) {
        setCommonFields((Film) tvShow);
        this.releaseDate = tvShow.getFirstAirDate();
    }

    // Private method to set common fields
    private void setCommonFields(Film film) {
        this.id = film.getId().getFilmId();
        this.title = film.getTitle();
        this.posterPath = film.getPosterPath();
        this.popularity = film.getPopularity();
        this.voteCount = film.getVoteCount();
        this.voteAverage = film.getVoteAverage();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
    
}
