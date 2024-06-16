package com.rraiz.movie_critic.feature.film.model.identifier;

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

    @Column(columnDefinition = "TEXT")
    private String department;

    @Column(columnDefinition = "TEXT")
    private String job;

    // Constructors
    public CrewId() {
    }

    public CrewId(Integer personId, FilmId filmId, String department, String job) {
        this.personId = personId;
        this.filmId = filmId;
        this.department = department;
        this.job = job;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    // Equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrewId that = (CrewId) o;
        return  Objects.equals(personId, that.personId) &&
                Objects.equals(filmId, that.filmId) &&
                Objects.equals(department, that.department) &&
                Objects.equals(job, that.job);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, filmId, department, job);
    }
}
