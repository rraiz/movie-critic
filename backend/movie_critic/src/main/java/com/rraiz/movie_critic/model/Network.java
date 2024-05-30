package com.rraiz.movie_critic.model;

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
    private String origin_country;

    @Column(nullable = true, name = "logo_path", columnDefinition = "TEXT")
    private String logoPath;

    @ManyToMany(mappedBy = "networks")
    @JsonBackReference
    private Set<TvShow> tvShows;

    @Column(nullable = true)
    private LocalDate lastUpdated;

    public Network() {
    }

    public Network(int id, String name, String origin_country, String logoPath, Set<TvShow> tvShows, LocalDate lastUpdated) {
        this.id = id;
        this.name = name;
        this.origin_country = origin_country;
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

    public String getOrigin_country() {
        return origin_country;
    }

    public void setOrigin_country(String origin_country) {
        this.origin_country = origin_country;
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
