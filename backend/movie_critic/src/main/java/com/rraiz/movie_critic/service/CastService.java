package com.rraiz.movie_critic.service;

import org.springframework.stereotype.Service;

import com.rraiz.movie_critic.model.Cast;
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
        castRepository.save(cast);
    }
    
}
