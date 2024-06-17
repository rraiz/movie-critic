package com.rraiz.movie_critic.feature.film.dto.filmDto;

import com.rraiz.movie_critic.feature.film.model.entity.Person;

public class PersonDto {

    private Integer id;
    private String name;
    private String profilePath;

    public PersonDto() {
    }

    public PersonDto(Person person) {
        this.id = person.getId();
        this.name = person.getName();
        this.profilePath = person.getProfilePath();
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

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }
    
}
