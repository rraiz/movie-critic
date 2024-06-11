package com.rraiz.movie_critic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rraiz.movie_critic.model.entity.Collection;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Integer>{

    
} 
