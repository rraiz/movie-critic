package com.rraiz.movie_critic.model;

import java.util.List;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
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
    private String backdrop_path;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String poster_path;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String original_name;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String original_language;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String overview;

    @Column(nullable = true)
    private Double popularity;

    @Column(nullable = true)
    private Integer vote_count;

    @Column(nullable = true)
    private Double vote_average;

    @ElementCollection
    @CollectionTable(name = "genres")
    @Column(name = "genre", nullable = true, columnDefinition = "TEXT")
    private List<String> genres;

    @ElementCollection
    @CollectionTable(name = "production_countries")
    @Column(name = "country", nullable = true, columnDefinition = "TEXT")
    private List<String> production_countries;

    @ElementCollection
    @CollectionTable(name = "spoken_languages")
    @Column(name = "language", nullable = true, columnDefinition = "TEXT")
    private List<String> spoken_languages;

    @ElementCollection
    @CollectionTable(name = "origin_countries")
    @Column(name = "country", nullable = true, columnDefinition = "TEXT")
    private List<String> origin_countries;

    @OneToMany(mappedBy = "film")
    private Set<Crew> crew;

    @OneToMany(mappedBy = "film")
    private Set<Cast> cast;

    @OneToMany(mappedBy = "film")
    private Set<Produced> produced;

    // Default Constructor
    public Film() {
    }

    // Parameterized Constructor
    public Film(FilmId id, String title, boolean adult, String homepage, String backdrop_path, String poster_path, String original_name, String original_language, String overview, Double popularity, Integer vote_count, Double vote_average, List<String> genres, List<String> production_countries, List<String> spoken_languages, List<String> origin_countries, Set<Crew> crew, Set<Cast> cast, Set<Produced> produced) {
        this.id = id;
        this.title = title;
        this.adult = adult;
        this.homepage = homepage;
        this.backdrop_path = backdrop_path;
        this.poster_path = poster_path;
        this.original_name = original_name;
        this.original_language = original_language;
        this.overview = overview;
        this.popularity = popularity;
        this.vote_count = vote_count;
        this.vote_average = vote_average;
        this.genres = genres;
        this.production_countries = production_countries;
        this.spoken_languages = spoken_languages;
        this.origin_countries = origin_countries;
        this.crew = crew;
        this.cast = cast;
        this.produced = produced;
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

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
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

    public Integer getVote_count() {
        return vote_count;
    }

    public void setVote_count(Integer vote_count) {
        this.vote_count = vote_count;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getProduction_countries() {
        return production_countries;
    }

    public void setProduction_countries(List<String> production_countries) {
        this.production_countries = production_countries;
    }

    public List<String> getSpoken_languages() {
        return spoken_languages;
    }

    public void setSpoken_languages(List<String> spoken_languages) {
        this.spoken_languages = spoken_languages;
    }

    public List<String> getOrigin_countries() {
        return origin_countries;
    }

    public void setOrigin_countries(List<String> origin_countries) {
        this.origin_countries = origin_countries;
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

    public Set<Produced> getProduced() {
        return produced;
    }

    public void setProduced(Set<Produced> produced) {
        this.produced = produced;
    }
}
