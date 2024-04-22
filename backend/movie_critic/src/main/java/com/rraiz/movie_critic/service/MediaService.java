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
        if (getMediaById(media.getTconst()) != null) {
            throw new IllegalStateException("Media with ID:" + media.getTconst() + " already exists");
        }
        mediaRepository.save(media);
    }

    @Transactional
    public Media getMediaById(int id) {
        return mediaRepository.findById(id).orElse(null);
    }

    @Transactional
    public Media getOrAddMedia(int mediaId)
    {
        Media media = getMediaById(mediaId);
        if (media == null) {
            media = new Media();
            media.setTconst(mediaId);
            addMedia(media);
        }
        return media; 
    }

    @Transactional
    public Media addForeignKey(int mediaId)
    {
        Media media = getMediaById(mediaId);
        if (media == null) {
            media = new Media();
            media.setTconst(mediaId);
            addMedia(media);
        }
        return media; 
    }

}
