package com.rraiz.movie_critic.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ProducedId implements Serializable  {

    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "film_id")
    private FilmId filmId;

    // Constructors
    public ProducedId() {
    }

    public ProducedId(Integer companyId, FilmId filmId) {
        this.companyId = companyId;
        this.filmId = filmId;
    }

    // Getters and setters
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public FilmId getFilmId() {
        return filmId;
    }

    public void setFilmId(FilmId filmId) {
        this.filmId = filmId;
    }

    // Equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProducedId that = (ProducedId) o;
        return Objects.equals(companyId, that.companyId) &&
               Objects.equals(filmId, that.filmId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, filmId);
    }
}
