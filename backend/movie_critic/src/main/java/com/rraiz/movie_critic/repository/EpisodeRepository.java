package com.rraiz.movie_critic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rraiz.movie_critic.model.Episode;

public interface EpisodeRepository extends JpaRepository<Episode, Integer>{

    
} 
