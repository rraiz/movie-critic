package com.rraiz.movie_critic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rraiz.movie_critic.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer>{
    
}
