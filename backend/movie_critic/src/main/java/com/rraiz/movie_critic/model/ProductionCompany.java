package com.rraiz.movie_critic.model;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "production_company")
public class ProductionCompany {

    @Id
    private int id;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String headquarters;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String homepage;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String logoPath;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String name;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String originCountry;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String parentCompany;

    @OneToMany(mappedBy = "productionCompany")
    private Set<Produced> produced;

    @Column(nullable = true)
    private LocalDate lastUpdated;

    public ProductionCompany() {
    }

    public ProductionCompany(int id, String description, String headquarters, String homepage, String logoPath, String name,
            String originCountry, String parentCompany, Set<Produced> produced, LocalDate lastUpdated) {
        this.id = id;
        this.description = description;
        this.headquarters = headquarters;
        this.homepage = homepage;
        this.logoPath = logoPath;
        this.name = name;
        this.originCountry = originCountry;
        this.parentCompany = parentCompany;
        this.produced = produced;
        this.lastUpdated = lastUpdated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public void setHeadquarters(String headquarters) {
        this.headquarters = headquarters;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
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

    public String getParentCompany() {
        return parentCompany;
    }

    public void setParentCompany(String parentCompany) {
        this.parentCompany = parentCompany;
    }

    public Set<Produced> getProduced() {
        return produced;
    }

    public void setProduced(Set<Produced> produced) {
        this.produced = produced;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
