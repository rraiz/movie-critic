package com.rraiz.movie_critic.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "person")
public class Person {

    @Id
    private int id;

    @Column(nullable = true)
    private String name;

    @Column(nullable = true)
    private boolean adult;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String biography;

    @Column(nullable = true, name = "birth_date")
    private LocalDate birthDate;

    @Column(nullable = true, name = "death_date")
    private LocalDate deathDate;

    @Column(nullable = true)
    private Integer gender;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String knownFor;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String birthPlace;

    @Column(nullable = true)
    private Integer popularity;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String profilePath;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String homepage;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String imdbId;

    @ElementCollection
    @CollectionTable(name = "also_known_as")
    @Column(name = "alias", nullable = true, columnDefinition = "TEXT")
    private List<String> alsoKnownAs;

    @OneToMany(mappedBy = "person")
    private Set<Crew> crew;

    @OneToMany(mappedBy = "person")
    private Set<Cast> cast;

    @OneToMany(mappedBy = "person")
    private Set<Created> created;

    // Default Constructor
    public Person() {
    }

    // Parameterized Constructor
    public Person(int id, String name, boolean adult, String biography, LocalDate birthDate, LocalDate deathDate, Integer gender, String knownFor, String birthPlace, Integer popularity, String profilePath, String homepage, String imdbId, List<String> alsoKnownAs, Set<Crew> crew, Set<Cast> cast, Set<Created> created) {
        this.id = id;
        this.name = name;
        this.adult = adult;
        this.biography = biography;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.gender = gender;
        this.knownFor = knownFor;
        this.birthPlace = birthPlace;
        this.popularity = popularity;
        this.profilePath = profilePath;
        this.homepage = homepage;
        this.imdbId = imdbId;
        this.alsoKnownAs = alsoKnownAs;
        this.crew = crew;
        this.cast = cast;
        this.created = created;
    }

    // Getters and setters
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

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(LocalDate deathDate) {
        this.deathDate = deathDate;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getKnownFor() {
        return knownFor;
    }

    public void setKnownFor(String knownFor) {
        this.knownFor = knownFor;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public List<String> getAlsoKnownAs() {
        return alsoKnownAs;
    }

    public void setAlsoKnownAs(List<String> alsoKnownAs) {
        this.alsoKnownAs = alsoKnownAs;
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

    public Set<Created> getCreated() {
        return created;
    }

    public void setCreated(Set<Created> created) {
        this.created = created;
    }
}
