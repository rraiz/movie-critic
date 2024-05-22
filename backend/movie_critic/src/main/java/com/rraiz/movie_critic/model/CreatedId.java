package com.rraiz.movie_critic.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CreatedId implements Serializable  {

    @Column(name = "person_id")
    private Integer personId;

    @Column(name = "tv_show_id")
    private FilmId tvShowId;

    // Constructors
    public CreatedId() {
    }

    public CreatedId(Integer personId, FilmId tvShowId) {
        this.personId = personId;
        this.tvShowId = tvShowId;
    }

    // Getters and setters
    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public FilmId getFilmId() {
        return tvShowId;
    }

    public void setFilmId(FilmId movieId) {
        this.tvShowId = movieId;
    }

    // Equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreatedId that = (CreatedId) o;
        return Objects.equals(personId, that.personId) &&
               Objects.equals(tvShowId, that.tvShowId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, tvShowId);
    }
}
