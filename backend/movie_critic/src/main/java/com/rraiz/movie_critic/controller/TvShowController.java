package com.rraiz.movie_critic.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rraiz.movie_critic.model.entity.TvShow;
import com.rraiz.movie_critic.service.TvShowService;
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
    public ResponseEntity<TvShow> getTvShowById(@PathVariable("tvShowId") int tvShowId) throws Exception {
        TvShow tvShow = tvShowService.getTvShowDetails(tvShowId);
        return ResponseUtil.createResponseEntity(tvShow);
    }
    
}
