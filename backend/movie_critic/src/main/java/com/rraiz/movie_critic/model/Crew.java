package com.rraiz.movie_critic.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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
    @JoinColumn(name = "film_id")
    private Film film;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String department;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String job;

    public Crew() {
    }

    public Crew(CrewId id, Person person, Film film, String department, String job) {
        this.id = id;
        this.person = person;
        this.film = film;
        this.department = department;
        this.job = job;
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
}
