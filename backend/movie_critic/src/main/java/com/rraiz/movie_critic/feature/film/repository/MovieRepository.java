package com.rraiz.movie_critic.feature.film.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rraiz.movie_critic.feature.film.model.entity.Movie;
import com.rraiz.movie_critic.feature.film.model.identifier.FilmId;

@Repository
public interface MovieRepository extends JpaRepository<Movie, FilmId>{
    
    List<Movie> findByTitleContaining(String name);
    
} 