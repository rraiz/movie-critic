package com.rraiz.movie_critic.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CrewId implements Serializable  {

    @Column(name = "person_id")
    private Integer personId;

    @Column(name = "film_id")
    private FilmId filmId;

    // Constructors
    public CrewId() {
    }

    public CrewId(Integer personId, FilmId filmId) {
        this.personId = personId;
        this.filmId = filmId;
    }

    // Getters and setters
    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
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
        CrewId that = (CrewId) o;
        return Objects.equals(personId, that.personId) &&
               Objects.equals(filmId, that.filmId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, filmId);
    }
}
