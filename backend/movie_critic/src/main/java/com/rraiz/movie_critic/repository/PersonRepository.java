package com.rraiz.movie_critic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rraiz.movie_critic.model.Person;

public interface PersonRepository extends JpaRepository<Person, Integer>{

    
} 