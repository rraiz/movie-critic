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

    @GetMapping("/{mediaId}-{personId}") 
    public ResponseEntity<Cast> getCastById(@PathVariable("mediaID") int mediaId, @PathVariable("personID") int personId) {
        CastId id = new CastId(mediaId, personId);
        Cast cast = castService.getCastById(id);
                
        if (cast != null) {
            return new ResponseEntity<>(cast, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
