package com.rraiz.movie_critic.model;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Cast {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String tconst;
    private String nconst;
    private String category;
    private String job;

    @ElementCollection
    private List<String> characters;

    public Cast(String tconst, String nconst, String category, String job, List<String> characters) 
    {
        this.tconst = tconst;
        this.nconst = nconst;
        this.category = category;
        this.job = job;
        this.characters = characters;
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTconst() {
        return tconst;
    }
    
    public void setTconst(String tconst) {
        this.tconst = tconst;
    }
    
    public String getNconst() {
        return nconst;
    }
    
    public void setNconst(String nconst) {
        this.nconst = nconst;
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
