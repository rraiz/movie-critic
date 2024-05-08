package com.rraiz.movie_critic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rraiz.movie_critic.model.Person;
import com.rraiz.movie_critic.service.PersonService;

@RestController
@RequestMapping(path = "/api/v1/persons")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/{id}") 
    public ResponseEntity<Person> getPersonById(@PathVariable("id") int id) {
        Person person = personService.getPersonById(id);

        if (person != null) {
            return new ResponseEntity<>(person, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
