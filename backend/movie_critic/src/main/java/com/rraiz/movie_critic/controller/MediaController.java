package com.rraiz.movie_critic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rraiz.movie_critic.model.Media;
import com.rraiz.movie_critic.service.MediaService;

@RestController
@RequestMapping(path = "/api/v1/medias")
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @GetMapping("/{id}") 
    public ResponseEntity<Media> getMediaById(@PathVariable("id") int id) {
        Media media = mediaService.getMediaById(id);
        System.out.println(media);

        if (media != null) {
            return new ResponseEntity<>(media, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
 

}
