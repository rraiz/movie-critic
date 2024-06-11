package com.rraiz.movie_critic.model.entity;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "network")
public class Network {

    @Id
    private int id;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String name;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String originCountry;

    @Column(nullable = true, name = "logo_path", columnDefinition = "TEXT")
    private String logoPath;

    @ManyToMany(mappedBy = "networks")
    @JsonBackReference
    private Set<TvShow> tvShows;

    @Column(nullable = true)
    private LocalDate lastUpdated;

    public Network() {
    }

    public Network(int id, String name, String originCountry, String logoPath, Set<TvShow> tvShows, LocalDate lastUpdated) {
        this.id = id;
        this.name = name;
        this.originCountry = originCountry;
        this.logoPath = logoPath;
        this.tvShows = tvShows;
        this.lastUpdated = lastUpdated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public Set<TvShow> getTvShows() {
        return tvShows;
    }

    public void setTvShows(Set<TvShow> tvShows) {
        this.tvShows = tvShows;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
