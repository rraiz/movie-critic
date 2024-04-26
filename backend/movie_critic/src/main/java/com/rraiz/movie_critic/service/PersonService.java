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

    @Transactional
    public Person getPersonById(int id) {
        return personRepository.findById(id).orElse(null);
    }

    @Transactional
    public Person addForeignKey(int personId)
    {
        Person person = getPersonById(personId);
        if (person == null) {
            person = new Person();
            person.setNconst(personId);
            addPerson(person);
        }
        return person; 
    }
    
    
}
