package com.rraiz.movie_critic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rraiz.movie_critic.model.Media;

public interface MediaRepository extends JpaRepository<Media, Integer>{
    
}
