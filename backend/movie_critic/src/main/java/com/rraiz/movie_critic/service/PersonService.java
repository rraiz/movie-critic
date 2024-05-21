package com.rraiz.movie_critic.service;

import org.springframework.stereotype.Service;

import com.rraiz.movie_critic.model.Cast;
import com.rraiz.movie_critic.model.Created;
import com.rraiz.movie_critic.model.Crew;
import com.rraiz.movie_critic.model.Person;
import com.rraiz.movie_critic.repository.CastRepository;
import com.rraiz.movie_critic.repository.CreatedRepository;
import com.rraiz.movie_critic.repository.CrewRepository;
import com.rraiz.movie_critic.repository.PersonRepository;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final CastRepository castRepository;
    private final CrewRepository crewRepository;
    private final CreatedRepository createdRepository;

    public PersonService(PersonRepository personRepository, CastRepository castRepository, CrewRepository crewRepository, CreatedRepository createdRepository) {
        this.personRepository = personRepository;
        this.castRepository = castRepository;
        this.crewRepository = crewRepository;
        this.createdRepository = createdRepository;
    }

    public void addPerson(Person person) {
        personRepository.save(person);
    }

    public void addCast(Cast cast) {
        castRepository.save(cast);
    }

    public void addCrew(Crew crew) {
        crewRepository.save(crew);
    }

    public void addCreated(Created created) {
        createdRepository.save(created);
    } 
}
