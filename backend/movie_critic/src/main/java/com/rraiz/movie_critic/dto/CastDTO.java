package com.rraiz.movie_critic.dto;

import com.rraiz.movie_critic.model.CastId;
import com.rraiz.movie_critic.model.Person;

import java.util.List;

/**
 * Data Transfer Object for the Cast entity.
 * Note: This DTO does not include a reference to the media entity to avoid redundancy and simplify the structure.
 */
public class CastDTO {

    private CastId id;
    private Person person;
    private String category;
    private String job;
    private List<String> characters;

    public CastDTO(CastId id, Person person, String category, String job, List<String> characters) {
        this.id = id;
        this.person = person;
        this.category = category;
        this.job = job;
        this.characters = characters;
    }

    public CastId getId() {
        return id;
    }

    public void setId(CastId id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public List<String> getCharacters() {
        return characters;
    }

    public void setCharacters(List<String> characters) {
        this.characters = characters;
    }
}
