package com.rraiz.movie_critic.feature.film.dto.filmDto;

import com.rraiz.movie_critic.feature.film.model.entity.Cast;

public class CastDto {

    private Integer personId;
    private Integer filmId;
    private Integer filmType;
    private String character;
    private Integer ordering;
    private String personName;
    private String profilePath;


    public CastDto() {
    }

    public CastDto(Cast cast) {
        this.personId = cast.getId().getPersonId();
        this.filmId = cast.getId().getFilmId().getFilmId();
        this.filmType = cast.getId().getFilmId().getFilmType();
        this.character = cast.getCharacter();
        this.ordering = cast.getOrdering();
        this.personName = cast.getPerson().getName();
        this.profilePath = cast.getPerson().getProfilePath();
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public Integer getFilmType() {
        return filmType;
    }

    public void setFilmType(Integer filmType) {
        this.filmType = filmType;
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

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }   
}
