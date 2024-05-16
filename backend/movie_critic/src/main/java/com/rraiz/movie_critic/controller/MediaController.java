package com.rraiz.movie_critic.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rraiz.movie_critic.dto.CastDTO;
import com.rraiz.movie_critic.dto.MediaCastDTO;
import com.rraiz.movie_critic.model.Media;
import com.rraiz.movie_critic.service.CastService;
import com.rraiz.movie_critic.service.MediaService;

@RestController
@RequestMapping(path = "/api/v1/medias")
public class MediaController {

    private final MediaService mediaService;
    private final CastService castService;

    public MediaController(MediaService mediaService, CastService castService) {
        this.mediaService = mediaService;
        this.castService = castService;
    }

    @GetMapping("/{id}") 
    public ResponseEntity<Media> getMediaById(@PathVariable("id") int id) {
        Media media = mediaService.getMediaById(id);

        if (media != null) {
            return new ResponseEntity<>(media, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves the cast for a specific media by its ID.
     * This method combines the media details with its associated cast members into a MediaCastDTO.
     * The purpose of this method is to return a singular media entity with a list of its associated casts to reduce redundancy for the frontend.
     * 
     * @param id the unique identifier of the media
     * @return a ResponseEntity containing a MediaCastDTO with the media and its cast,
     *         and an HTTP status of OK if the cast is found, or NOT_FOUND if no cast is associated with the media
     */
    @GetMapping("/{id}/cast")
    public ResponseEntity<MediaCastDTO> getCastByMediaId(@PathVariable("id") int id) {
        Media media = mediaService.getMediaById(id);
        List<CastDTO> castList = castService.getCastsByMediaId(id);
        MediaCastDTO mediaCastDTO = new MediaCastDTO(media, castList);

        if (castList != null && !castList.isEmpty()) {
            return new ResponseEntity<>(mediaCastDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
 

}
