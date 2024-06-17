package com.rraiz.movie_critic.feature.film.dto.personDto;

import com.rraiz.movie_critic.feature.film.model.entity.Cast;

public class CastDto {

    private Integer filmId;
    private Integer filmType;
    private String filmName;
    private String character;
    private Integer ordering;


    public CastDto() {
    }

    public CastDto(Cast cast) {
        this.filmId = cast.getId().getFilmId().getFilmId();
        this.filmType = cast.getId().getFilmId().getFilmType();
        this.filmName = cast.getFilm().getTitle();
        this.character = cast.getCharacter();
        this.ordering = cast.getOrdering();
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

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
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
    
}
