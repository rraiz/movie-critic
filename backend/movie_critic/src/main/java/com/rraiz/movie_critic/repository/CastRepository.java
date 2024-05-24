package com.rraiz.movie_critic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rraiz.movie_critic.model.Cast;
import com.rraiz.movie_critic.model.CastId;

@Repository
public interface CastRepository extends JpaRepository<Cast, CastId>{


}
