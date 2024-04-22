package com.rraiz.movie_critic.service;

import org.springframework.stereotype.Service;

import com.rraiz.movie_critic.model.Cast;
import com.rraiz.movie_critic.model.CastId;
import com.rraiz.movie_critic.repository.CastRepository;

import jakarta.transaction.Transactional;

@Service
public class CastService {

    private final CastRepository castRepository;

    public CastService(CastRepository castRepository) {
        this.castRepository = castRepository;
    }

    @Transactional
    public void addCast(Cast cast) {
        if (getCastById(cast.getId()) != null) {
            throw new IllegalStateException("Cast with ID:" + cast.getId() + " already exists");
        }
        castRepository.save(cast);
    }

    @Transactional
    public Cast getCastById(CastId id) {
        return castRepository.findById(id).orElse(null);
    }

    @Transactional
    public Cast getOrAddCast(CastId castId)
    {
        Cast cast = getCastById(castId);
        if (cast == null) {
            cast = new Cast();
            cast.setId(castId);
            addCast(cast);
        }
        return cast; 
    }

    @Transactional
    public Cast addForeignKey(CastId castId)
    {
        Cast cast = getCastById(castId);
        if (cast == null) {
            cast = new Cast();
            cast.setId(castId);
            addCast(cast);
        }
        return cast; 
    }
    
}
