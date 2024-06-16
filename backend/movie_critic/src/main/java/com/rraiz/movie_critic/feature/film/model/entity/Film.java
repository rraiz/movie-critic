package com.rraiz.movie_critic.feature.film.model.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rraiz.movie_critic.feature.film.model.identifier.FilmId;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Film {

    @EmbeddedId
    private FilmId id;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String title;

    @Column(nullable = true)
    private boolean adult;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String homepage;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String backdropPath;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String posterPath;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String originalName;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String originalLanguage;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String overview;

    @Column(nullable = true)
    private Double popularity;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String tagline;

    @Column(nullable = true)
    private Integer voteCount;

    @Column(nullable = true)
    private Double voteAverage;

    @ElementCollection
    @CollectionTable(name = "genres")
    @Column(name = "genre", nullable = true, columnDefinition = "TEXT")
    private List<String> genres;

    @ElementCollection
    @CollectionTable(name = "production_countries")
    @Column(name = "country", nullable = true, columnDefinition = "TEXT")
    private List<String> productionCountries;

    @ElementCollection
    @CollectionTable(name = "spoken_languages")
    @Column(name = "language", nullable = true, columnDefinition = "TEXT")
    private List<String> spokenLanguages;

    @ElementCollection
    @CollectionTable(name = "origin_countries")
    @Column(name = "country", nullable = true, columnDefinition = "TEXT")
    private List<String> originCountries;

    @OneToMany(mappedBy = "film")
    @JsonManagedReference
    private Set<Crew> crew;

    @OneToMany(mappedBy = "film")
    @JsonManagedReference
    private Set<Cast> cast;

    @ManyToMany
    @JoinTable(
        name = "produced_film",
        joinColumns = {
            @JoinColumn(name = "film_id"),
            @JoinColumn(name = "film_type")
        },
        inverseJoinColumns = @JoinColumn(name = "production_company_id")
    )
    @JsonManagedReference
    private Set<ProductionCompany> produced;

    @Column(nullable = true)
    private LocalDate lastUpdated;

    // Default Constructor
    public Film() {
    }

    // Parameterized Constructor
    public Film(FilmId id, String title, boolean adult, String homepage, String backdropPath, String posterPath, String originalName, String originalLanguage, String overview, Double popularity, String tagline, Integer voteCount, Double voteAverage, List<String> genres, List<String> productionCountries, List<String> spokenLanguages, List<String> originCountries, Set<Crew> crew, Set<Cast> cast, Set<ProductionCompany> produced, LocalDate lastUpdated) {
        this.id = id;
        this.title = title;
        this.adult = adult;
        this.homepage = homepage;
        this.backdropPath = backdropPath;
        this.posterPath = posterPath;
        this.originalName = originalName;
        this.originalLanguage = originalLanguage;
        this.overview = overview;
        this.popularity = popularity;
        this.tagline = tagline;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.genres = genres;
        this.productionCountries = productionCountries;
        this.spokenLanguages = spokenLanguages;
        this.originCountries = originCountries;
        this.crew = crew;
        this.cast = cast;
        this.produced = produced;
        this.lastUpdated = lastUpdated;
    }

    // Getters and Setters
    public FilmId getId() {
        return id;
    }

    public void setId(FilmId id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(List<String> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public List<String> getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(List<String> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public List<String> getOriginCountries() {
        return originCountries;
    }

    public void setOriginCountries(List<String> originCountries) {
        this.originCountries = originCountries;
    }

    public Set<Crew> getCrew() {
        return crew;
    }

    public void setCrew(Set<Crew> crew) {
        this.crew = crew;
    }

    public Set<Cast> getCast() {
        return cast;
    }

    public void setCast(Set<Cast> cast) {
        this.cast = cast;
    }

    public Set<ProductionCompany> getProduced() {
        return produced;
    }

    public void setProduced(Set<ProductionCompany> produced) {
        this.produced = produced;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
