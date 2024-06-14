package com.rraiz.movie_critic.model.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rraiz.movie_critic.model.identifier.CrewId;

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

    @Column(nullable = true, columnDefinition = "TEXT")
    private String personName; // This is a denormalized field

    @Column(nullable = true, columnDefinition = "TEXT")
    private String profilePath; // This is a denormalized field

    @Column(nullable = true, columnDefinition = "TEXT")
    private String filmTitle; // This is a denormalized field

    @Column(nullable = true)
    private LocalDate lastUpdated;

    public Crew() {
    }

    public Crew(CrewId id, Person person, Film film, String personName, String profilePath, String filmTitle, LocalDate lastUpdated) {
        this.id = id;
        this.person = person;
        this.film = film;
        this.personName = personName;
        this.profilePath = profilePath;
        this.filmTitle = filmTitle;
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

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilPath) {
        this.profilePath = profilPath;
    }

    public String getFilmTitle() {
        return filmTitle;
    }

    public void setFilmTitle(String filmTitle) {
        this.filmTitle = filmTitle;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
