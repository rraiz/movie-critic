package com.rraiz.movie_critic.model;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "media_cast")
public class Cast {


    @EmbeddedId
    private CastId id;

    @ManyToOne
    @MapsId("tconst")
    @JoinColumn(name = "tconst")
    private Media media;

    @ManyToOne
    @MapsId("nconst")
    @JoinColumn(name = "nconst")
    private Person person;
    
    @Column(nullable = true, columnDefinition = "TEXT")
    private String category;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String job;

    @ElementCollection
    @CollectionTable(name = "media_cast_characters")
    @Column(name = "character", nullable = true, columnDefinition = "TEXT")
    private List<String> characters;

    public Cast() {
    }

    public Cast(CastId id, Media media, Person person, String category, String job, List<String> characters) 
    {
        this.id = id;
        this.media = media;
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
    
    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
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
