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
@Table(name = "produced")
public class Produced {

    @EmbeddedId
    private ProducedId id;

    @ManyToOne
    @MapsId("companyId")
    @JoinColumn(name = "company_id")
    @JsonBackReference
    private ProductionCompany productionCompany;

    @ManyToOne
    @MapsId("filmId")
    @JoinColumns({
        @JoinColumn(name = "film_id"),
        @JoinColumn(name = "film_type")
    })
    @JsonBackReference
    private Film film;

    @Column (nullable = true)
    private LocalDate lastUpdated;

    public Produced() {
    }

    public Produced(ProducedId id, ProductionCompany productionCompany, Film film, LocalDate lastUpdated) {
        this.id = id;
        this.productionCompany = productionCompany;
        this.film = film;
        this.lastUpdated = lastUpdated;
    }

    public ProducedId getId() {
        return id;
    }

    public void setId(ProducedId id) {
        this.id = id;
    }

    public ProductionCompany getProductionCompany() {
        return productionCompany;
    }

    public void setProductionCompany(ProductionCompany productionCompany) {
        this.productionCompany = productionCompany;
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

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
