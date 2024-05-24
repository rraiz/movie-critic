package com.rraiz.movie_critic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rraiz.movie_critic.model.Produced;
import com.rraiz.movie_critic.model.ProducedId;

@Repository
public interface ProducedRepository extends JpaRepository<Produced, ProducedId>{

    
} 