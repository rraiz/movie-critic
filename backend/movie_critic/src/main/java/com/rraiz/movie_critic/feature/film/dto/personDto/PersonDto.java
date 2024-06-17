package com.rraiz.movie_critic.feature.film.dto.personDto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.rraiz.movie_critic.feature.film.model.entity.Cast;
import com.rraiz.movie_critic.feature.film.model.entity.Crew;
import com.rraiz.movie_critic.feature.film.model.entity.Person;

public class PersonDto {

    private Integer id;
    private String name;
    private String biography;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private int gender;
    private String knownFor;
    private String birthPlace;
    private Double popularity;
    private String profilePath;
    private String imdbId;
    private String homepage;
    private LocalDate lastUpdated;

    private List<String> alsoKnownAs;

    private Set<CastDto> cast;
    private Set<CrewDto> crew;

    public PersonDto() {
    }

    public PersonDto(Person person) {
        this.id = person.getId();
        this.name = person.getName();
        this.biography = person.getBiography();
        this.birthDate = person.getBirthDate();
        this.deathDate = person.getDeathDate();
        this.gender = person.getGender();
        this.knownFor = person.getKnownFor();
        this.birthPlace = person.getBirthPlace();
        this.popularity = person.getPopularity();
        this.profilePath = person.getProfilePath();
        this.imdbId = person.getImdbId();
        this.homepage = person.getHomepage();
        this.lastUpdated = person.getLastUpdated();

        this.alsoKnownAs = person.getAlsoKnownAs();
        
        Set<Cast> personCast = person.getCast();
        Set<CastDto> cast = null;
        if(personCast != null)
        {
            cast = new HashSet<>();
            for(Cast c : personCast)
            {
                cast.add(new CastDto(c));
            }
        }
        this.cast = cast;

        Set<Crew> personCrew = person.getCrew();
        Set<CrewDto> crew = null;
        if(personCrew != null)
        {
            crew = new HashSet<>();
            for(Crew c : personCrew)
            {
                crew.add(new CrewDto(c));
            }
        }
        this.crew = crew;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
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

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public List<String> getAlsoKnownAs() {
        return alsoKnownAs;
    }

    public void setAlsoKnownAs(List<String> alsoKnownAs) {
        this.alsoKnownAs = alsoKnownAs;
    }

    public Set<CastDto> getCast() {
        return cast;
    }

    public void setCast(Set<CastDto> cast) {
        this.cast = cast;
    }

    public Set<CrewDto> getCrew() {
        return crew;
    }

    public void setCrew(Set<CrewDto> crew) {
        this.crew = crew;
    }

}
