package com.rraiz.movie_critic.feature.film.service;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.rraiz.movie_critic.feature.film.model.entity.TvShow;
import com.rraiz.movie_critic.feature.film.model.identifier.FilmId;
import com.rraiz.movie_critic.feature.film.repository.TvShowRepository;
import com.rraiz.movie_critic.util.TMDBApiService;

import jakarta.transaction.Transactional;

@Service
public class TvShowService {

    private final TvShowRepository tvShowRepository;
    private final TMDBApiService tmdbApiService;


    public TvShowService(TvShowRepository tvShowRepository, @Lazy TMDBApiService tmdbApiService) {
        this.tvShowRepository = tvShowRepository;
        this.tmdbApiService = tmdbApiService;
    }

    /* Tv Show */
    @Transactional
    public void addTvShow(TvShow tvShow) {
        tvShowRepository.save(tvShow);
    }

    @Transactional
    public TvShow getTvShowById(int tvShowId) {
        FilmId id = new FilmId(tvShowId, 1);
        return tvShowRepository.findById(id).orElse(null);
    }

    public TvShow getTvShowDetails(int tvShowId) throws Exception {
        TvShow tv = getTvShowById(tvShowId);
        if (tv == null || tv.getLastUpdated() == null || true){
            tv = tmdbApiService.fetchTvShow(tvShowId);
            if (tv != null)
                addTvShow(tv);;
        }
        return tv;
    }


    public List<TvShow> getTvShowsByName(String tvShowName) {
        return tvShowRepository.findByTitleContaining(tvShowName);
    }


}
