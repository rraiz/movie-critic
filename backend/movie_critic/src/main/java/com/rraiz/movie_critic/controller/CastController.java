package com.rraiz.movie_critic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rraiz.movie_critic.model.Cast;
import com.rraiz.movie_critic.model.CastId;
import com.rraiz.movie_critic.service.CastService;

@RestController
@RequestMapping(path = "/api/v1/casts")
public class CastController {

    private final CastService castService;

    public CastController(CastService castService) {
        this.castService = castService;
    }

    @GetMapping("/{id1}-{id2}") 
    public ResponseEntity<Cast> getCastById(@PathVariable("id1") int castId1, @PathVariable("id2") int castId2) {
        CastId id = new CastId(castId1, castId2);
        Cast cast = castService.getCastById(id);

        System.out.println(cast);
        
        if (cast != null) {
            return new ResponseEntity<>(cast, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
