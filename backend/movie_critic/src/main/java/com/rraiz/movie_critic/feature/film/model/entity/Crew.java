package com.rraiz.movie_critic.feature.film.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rraiz.movie_critic.feature.film.model.identifier.CrewId;

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
    @JsonBackReference
    private Person person;

    @ManyToOne
    @MapsId("filmId")
    @JoinColumns({
        @JoinColumn(name = "film_id"),
        @JoinColumn(name = "film_type")
    })
    @JsonBackReference
    private Film film;

    public Crew() {
    }

    public Crew(CrewId id, Person person, Film film) {
        this.id = id;
        this.person = person;
        this.film = film;
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
}
