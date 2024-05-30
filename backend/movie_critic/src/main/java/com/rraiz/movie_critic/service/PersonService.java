package com.rraiz.movie_critic.service;

import org.springframework.stereotype.Service;

import com.rraiz.movie_critic.model.Cast;
import com.rraiz.movie_critic.model.Crew;
import com.rraiz.movie_critic.model.Person;
import com.rraiz.movie_critic.repository.CastRepository;
import com.rraiz.movie_critic.repository.CrewRepository;
import com.rraiz.movie_critic.repository.PersonRepository;

import jakarta.transaction.Transactional;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final CastRepository castRepository;
    private final CrewRepository crewRepository;

    public PersonService(PersonRepository personRepository, CastRepository castRepository, CrewRepository crewRepository) {
        this.personRepository = personRepository;
        this.castRepository = castRepository;
        this.crewRepository = crewRepository;
    }

    /* Person */
    @Transactional
    public void addPerson(Person person) {
        personRepository.save(person);
    }

    @Transactional
    public Person getPersonById(int personId) {
        return personRepository.findById(personId).orElse(null);
    }

    /* Cast */
    public void addCast(Cast cast) {
        castRepository.save(cast);
    }

    /* Crew */
    public void addCrew(Crew crew) {
        crewRepository.save(crew);
    }
}
