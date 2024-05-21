package com.rraiz.movie_critic.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "collection")
public class Collection {

    @Id
    private int id;

    @Column(nullable = true)
    private String name;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String overview;

    @Column(nullable = true, name = "poster_path", columnDefinition = "TEXT")
    private String posterPath;

    @Column(nullable = true, name = "backdrop_path", columnDefinition = "TEXT")
    private String backdropPath;

    @OneToMany(mappedBy = "collection")
    private Set<Movie> movies;

    // Default Constructor
    public Collection() {
    }

    // Parameterized Constructor
    public Collection(int id, String name, String overview, String posterPath, String backdropPath, Set<Movie> movies) {
        this.id = id;
        this.name = name;
        this.overview = overview;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.movies = movies;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
}
