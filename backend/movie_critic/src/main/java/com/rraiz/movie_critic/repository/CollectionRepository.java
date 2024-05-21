package com.rraiz.movie_critic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rraiz.movie_critic.model.Collection;

public interface CollectionRepository extends JpaRepository<Collection, Integer>{

    
} 
