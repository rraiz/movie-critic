package com.rraiz.movie_critic.feature.film.dto.filmDto;

import java.time.LocalDate;

import com.rraiz.movie_critic.feature.film.model.entity.Collection;
import com.rraiz.movie_critic.feature.film.model.entity.Movie;

public class MovieDto extends FilmDto{

    private Long budget;
    private LocalDate releaseDate;
    private long revenue;
    private Integer runtime;
    private Collection collection;
    private LocalDate lastUpdated;

    public MovieDto() {
    }

    public MovieDto(Movie movie)
    {
        super(movie);
        this.budget = movie.getBudget();
        this.releaseDate = movie.getReleaseDate();
        this.revenue = movie.getRevenue();
        this.runtime = movie.getRuntime();
        this.collection = movie.getCollection();
        this.lastUpdated = movie.getLastUpdated();
    }

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
}
