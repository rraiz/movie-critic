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
@Table(name = "film_created")
public class Created {

    @EmbeddedId
    private CreatedId id;

    @ManyToOne
    @MapsId("personId")
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "film_id", insertable = false, updatable = false),
        @JoinColumn(name = "film_type", insertable = false, updatable = false)
    })
    private TvShow tvShow;

    @Column(nullable = true)
    private LocalDate lastUpdated;

    public Created() {
    }

    public Created(CreatedId id, Person person, TvShow tvShow, LocalDate lastUpdated) {
        this.id = id;
        this.person = person;
        this.tvShow = tvShow;
        this.lastUpdated = lastUpdated;
    }

    public CreatedId getId() {
        return id;
    }

    public void setId(CreatedId id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public TvShow getTvShow() {
        return tvShow;
    }

    public void setTvShow(TvShow tvShow) {
        this.tvShow = tvShow;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
