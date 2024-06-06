package com.rraiz.movie_critic.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "film_cast")
public class Cast {

    @EmbeddedId
    private CastId id;

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
    private String character;

    @Column(nullable = true)
    private Integer ordering;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String personName; // This is a denormalized field

    @Column(nullable = true, columnDefinition = "TEXT")
    private String profilPath; // This is a denormalized field

    @Column(nullable = true, columnDefinition = "TEXT")
    private String filmTitle; // This is a denormalized field

    @Column(nullable = true)
    private LocalDate lastUpdated;

    public Cast() {
    }

    public Cast(CastId id, Person person, Film film, String character, Integer ordering, String personName, String profilePath, String filmTitle, LocalDate lastUpdated) {
        this.id = id;
        this.person = person;
        this.film = film;
        this.character = character;
        this.ordering = ordering;
        this.personName = personName;
        this.profilPath = profilePath;
        this.filmTitle = filmTitle;
        this.lastUpdated = lastUpdated;
    }

    public CastId getId() {
        return id;
    }

    public void setId(CastId id) {
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

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public Integer getOrdering() {
        return ordering;
    }

    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
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

    public String getProfilPath() {
        return profilPath;
    }

    public void setProfilPath(String profilPath) {
        this.profilPath = profilPath;
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
