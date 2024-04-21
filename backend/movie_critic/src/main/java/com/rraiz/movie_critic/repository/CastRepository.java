package com.rraiz.movie_critic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rraiz.movie_critic.model.Cast;

public interface CastRepository extends JpaRepository<Cast, Integer>{


}
