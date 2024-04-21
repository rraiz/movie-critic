package com.rraiz.movie_critic.service;

import org.springframework.stereotype.Service;

import com.rraiz.movie_critic.model.Media;
import com.rraiz.movie_critic.repository.MediaRepository;

import jakarta.transaction.Transactional;

@Service
public class MediaService {

    private final MediaRepository mediaRepository;

    public MediaService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    @Transactional
    public void addMedia(Media media) {
        mediaRepository.save(media);
    }

}
