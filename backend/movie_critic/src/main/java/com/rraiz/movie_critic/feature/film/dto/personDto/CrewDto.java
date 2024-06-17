package com.rraiz.movie_critic.feature.film.dto.personDto;

import com.rraiz.movie_critic.feature.film.model.entity.Crew;

public class CrewDto {

    private Integer filmId;
    private Integer filmType;
    private String filmName;
    private String department;
    private String job;

    public CrewDto() {
    }

    public CrewDto(Crew crew) {
        this.filmId = crew.getId().getFilmId().getFilmId();
        this.filmType = crew.getId().getFilmId().getFilmType();
        this.filmName = crew.getFilm().getTitle();
        this.department = crew.getId().getDepartment();
        this.job = crew.getId().getJob();
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
    
}
