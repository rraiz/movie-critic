package com.rraiz.movie_critic.model;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "person")
public class Person {

    @Id
    private int nconst;
    private String name;

    @Column(nullable = true)
    private Integer birthYear;

    @Column(nullable = true)
    private Integer deathYear;

    @ElementCollection
    @CollectionTable(name = "person_primary_profession")
    @Column(name = "profession", nullable = true, columnDefinition = "TEXT")
    private List<String> primaryProfession;

    public Person() {
    }

    public Person(int nconst, String name, Integer birthYear, Integer deathYear, List<String> primaryProfession) 
    {
        this.nconst = nconst;
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
        this.primaryProfession = primaryProfession;
    }

    public int getNconst() {
        return nconst;
    }

    public void setNconst(int nconst) {
        this.nconst = nconst;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public List<String> getPrimaryProfession() {
        return primaryProfession;
    }

    public void setPrimaryProfession(List<String> primaryProfession) {
        this.primaryProfession = primaryProfession;
    }
}
