package com.rraiz.movie_critic.service;

import org.springframework.stereotype.Service;

import com.rraiz.movie_critic.model.Person;
import com.rraiz.movie_critic.repository.PersonRepository;

import jakarta.transaction.Transactional;

@Service
public class PersonService {
    
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional
    public void addPerson(Person person) {
        personRepository.save(person);
    }
    
}
