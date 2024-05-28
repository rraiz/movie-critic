package com.rraiz.movie_critic.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "film_crew")
public class Crew {

    @EmbeddedId
    private CrewId id;

    @ManyToOne
    @MapsId("personId")
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @MapsId("filmId")
    @JoinColumns({
        @JoinColumn(name = "film_id"),
        @JoinColumn(name = "film_type")
    })
    private Film film;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String department;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String job;

    @Column(nullable = true)
    private LocalDate lastUpdated;

    public Crew() {
    }

    public Crew(CrewId id, Person person, Film film, String department, String job, LocalDate lastUpdated) {
        this.id = id;
        this.person = person;
        this.film = film;
        this.department = department;
        this.job = job;
        this.lastUpdated = lastUpdated;
    }

    public CrewId getId() {
        return id;
    }

    public void setId(CrewId id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
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

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
