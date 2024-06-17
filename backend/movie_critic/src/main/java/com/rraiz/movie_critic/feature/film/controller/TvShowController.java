package com.rraiz.movie_critic.feature.film.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rraiz.movie_critic.feature.film.dto.filmDto.TvShowDto;
import com.rraiz.movie_critic.feature.film.model.entity.TvShow;
import com.rraiz.movie_critic.feature.film.service.TvShowService;
import com.rraiz.movie_critic.util.ResponseUtil;

@RestController
@RequestMapping(path = "/api/v1/tv")
@CrossOrigin(origins = "http://localhost:5173")
public class TvShowController {

    private final TvShowService tvShowService;

    public TvShowController(TvShowService tvShowService) {
        this.tvShowService = tvShowService;
    }

    @GetMapping("/{tvShowId}")
    public ResponseEntity<TvShowDto> getTvShowById(@PathVariable int tvShowId) throws Exception {
        TvShow tvShow = tvShowService.getTvShowDetails(tvShowId);
        return ResponseUtil.createResponseEntity(new TvShowDto(tvShow));
    }
    
}
