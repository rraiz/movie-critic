package com.rraiz.movie_critic.service;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.rraiz.movie_critic.model.entity.Episode;
import com.rraiz.movie_critic.model.entity.Season;
import com.rraiz.movie_critic.model.entity.TvShow;
import com.rraiz.movie_critic.model.identifier.FilmId;
import com.rraiz.movie_critic.repository.EpisodeRepository;
import com.rraiz.movie_critic.repository.SeasonRepository;
import com.rraiz.movie_critic.repository.TvShowRepository;

import jakarta.transaction.Transactional;

@Service
public class TvShowService {

    private final TvShowRepository tvShowRepository;
    private final SeasonRepository seasonRepository;
    private final EpisodeRepository episodeRepository;

    private final TMDBApiService tmdbApiService;


    public TvShowService(TvShowRepository tvShowRepository, SeasonRepository seasonRepository, EpisodeRepository episodeRepository, @Lazy TMDBApiService tmdbApiService) {
        this.tvShowRepository = tvShowRepository;
        this.seasonRepository = seasonRepository;
        this.episodeRepository = episodeRepository;
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
        if (tv == null) {
            tv = tmdbApiService.fetchTvShowDetails(tvShowId);
            if (tv != null)
                addTvShow(tv);;
        }
        return tv;
    }

    /* Season */
    @Transactional
    public void addSeason(Season season) {
        seasonRepository.save(season);
    }

    @Transactional
    public Season getSeasonById(int seasonId) {
        return seasonRepository.findById(seasonId).orElse(null);
    }

    /* Episode */
    @Transactional
    public void addEpisode(Episode episode) {
        episodeRepository.save(episode);
    }

    @Transactional
    public Episode getEpisodeById(int episode) {
        return episodeRepository.findById(episode).orElse(null);
    }

    public List<TvShow> getTvShowsByName(String tvShowName) {
        return tvShowRepository.findByTitleContaining(tvShowName);
    }


}
