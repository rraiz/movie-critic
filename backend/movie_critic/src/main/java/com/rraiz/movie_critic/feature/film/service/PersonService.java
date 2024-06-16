package com.rraiz.movie_critic.feature.film.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.rraiz.movie_critic.feature.film.model.entity.Cast;
import com.rraiz.movie_critic.feature.film.model.entity.Crew;
import com.rraiz.movie_critic.feature.film.model.entity.Person;
import com.rraiz.movie_critic.feature.film.model.identifier.CastId;
import com.rraiz.movie_critic.feature.film.model.identifier.CrewId;
import com.rraiz.movie_critic.feature.film.repository.CastRepository;
import com.rraiz.movie_critic.feature.film.repository.CrewRepository;
import com.rraiz.movie_critic.feature.film.repository.PersonRepository;

import jakarta.transaction.Transactional;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final CastRepository castRepository;
    private final CrewRepository crewRepository;

    private final TMDBApiService tmdbApiService;

    public PersonService(PersonRepository personRepository, CastRepository castRepository, CrewRepository crewRepository, @Lazy TMDBApiService tmdbApiService) {
        this.personRepository = personRepository;
        this.castRepository = castRepository;
        this.crewRepository = crewRepository;
        this.tmdbApiService = tmdbApiService;
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

    public Person getPersonDetails (int personId) throws Exception {
        Person p = getPersonById(personId);
        if (p == null || p.getLastUpdated() == null) {
            p = tmdbApiService.fetchPersonDetails(personId);
            if (p != null)
                addPerson(p);
        }
        return p;
    }

    /* Cast */
    @Transactional
    public void addCast(Cast cast) {
        castRepository.save(cast);
    }

    @Transactional
    public Cast getCastById(CastId castId) {
        return castRepository.findById(castId).orElse(null);
    }

    /* Crew */
    @Transactional
    public void addCrew(Crew crew) {
        crewRepository.save(crew);
    }

    @Transactional
    public Crew getCrewById(CrewId crewId) {
        return crewRepository.findById(crewId).orElse(null);
    }
}
