package com.rraiz.movie_critic.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class FilmId {

    @Column(name = "film_id")
    private Integer filmId;

    @Column(name = "film_type")
    private Integer filmType;

    // Constructors
    public FilmId() {
    }

    public FilmId(Integer filmId, Integer filmType) {
        this.filmId = filmId;
        this.filmType = filmType;
    }

    // Getters and setters
    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public Integer getFilmType() {
        return filmType;
    }

    public void setFilmType(Integer filmType) {
        this.filmType = filmType;
    }

    // Equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmId that = (FilmId) o;
        return filmId.equals(that.filmId) &&
               filmType.equals(that.filmType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmId, filmType);
    }

    
}
