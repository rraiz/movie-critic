package com.rraiz.movie_critic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rraiz.movie_critic.model.FilmId;
import com.rraiz.movie_critic.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, FilmId>{

    
} 