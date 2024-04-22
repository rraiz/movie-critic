package com.rraiz.movie_critic.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class CastId implements Serializable {

    private int tconst;
    private int nconst;

    // Constructors
    
    public CastId() {
    }

    public CastId(int tconst, int nconst) {
        this.tconst = tconst;
        this.nconst = nconst;
    }

    // Getters and setters
    
    public int getTconst() {
        return tconst;
    }

    public void setTconst(int tconst) {
        this.tconst = tconst;
    }

    public int getNconst() {
        return nconst;
    }

    public void setNconst(int nconst) {
        this.nconst = nconst;
    }

    // Equals and hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CastId that = (CastId) o;
        return Objects.equals(tconst, that.tconst) &&
               Objects.equals(nconst, that.nconst);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tconst, nconst);
    }
}
