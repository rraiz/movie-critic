package com.rraiz.movie_critic.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "movie")
public class Movie extends Film {

    @Column(nullable = true, columnDefinition = "TEXT")
    private String tagline;

    @Column(nullable = true)
    private Integer budget;

    @Column(nullable = true, name = "release_date")
    private LocalDate releaseDate;

    @Column(nullable = true)
    private Integer revenue;

    @Column(nullable = true)
    private Integer runtime;

    @ManyToOne
    @JoinColumn(name = "collection_id")
    private Collection collection;

    // Default Constructor
    public Movie() {
        super();
    }

    // Parameterized Constructor
    public Movie(FilmId id, String title, boolean adult, String homepage, String backdrop_path, String poster_path, String original_name, String original_language, String overview, Double popularity, Integer vote_count, Double vote_average, List<String> genres, List<String> production_countries, List<String> spoken_languages, List<String> origin_countries, Set<Crew> crew, Set<Cast> cast, Set<Produced> produced, String tagline, Integer budget, LocalDate releaseDate, Integer revenue, Integer runtime, Collection collection) {
        super(id, title, adult, homepage, backdrop_path, poster_path, original_name, original_language, overview, popularity, vote_count, vote_average, genres, production_countries, spoken_languages, origin_countries, crew, cast, produced);
        this.tagline = tagline;
        this.budget = budget;
        this.releaseDate = releaseDate;
        this.revenue = revenue;
        this.runtime = runtime;
        this.collection = collection;
    }

    // Getters and Setters
    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public void setRevenue(Integer revenue) {
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
}
