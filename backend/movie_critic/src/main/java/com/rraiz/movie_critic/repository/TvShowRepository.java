package com.rraiz.movie_critic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rraiz.movie_critic.model.FilmId;
import com.rraiz.movie_critic.model.TvShow;

@Repository
public interface TvShowRepository extends JpaRepository<TvShow, FilmId>{

    
} 