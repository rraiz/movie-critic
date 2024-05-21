package com.rraiz.movie_critic.service;

import org.springframework.stereotype.Service;

import com.rraiz.movie_critic.model.Episode;
import com.rraiz.movie_critic.model.Season;
import com.rraiz.movie_critic.model.TvShow;
import com.rraiz.movie_critic.repository.EpisodeRepository;
import com.rraiz.movie_critic.repository.SeasonRepository;
import com.rraiz.movie_critic.repository.TvShowRepository;

@Service
public class TvShowService {

    private final TvShowRepository tvShowRepository;
    private final SeasonRepository seasonRepository;
    private final EpisodeRepository episodeRepository;

    public TvShowService(TvShowRepository tvShowRepository, SeasonRepository seasonRepository, EpisodeRepository episodeRepository) {
        this.tvShowRepository = tvShowRepository;
        this.seasonRepository = seasonRepository;
        this.episodeRepository = episodeRepository;
    }

    public void addTvShow(TvShow tvShow) {
        tvShowRepository.save(tvShow);
    }

    public void addSeason(Season season) {
        seasonRepository.save(season);
    }

    public void addEpisode(Episode episode) {
        episodeRepository.save(episode);
    }
}
