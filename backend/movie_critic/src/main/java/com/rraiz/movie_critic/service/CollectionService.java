package com.rraiz.movie_critic.service;

import org.springframework.stereotype.Service;

import com.rraiz.movie_critic.model.entity.Collection;
import com.rraiz.movie_critic.repository.CollectionRepository;

import jakarta.transaction.Transactional;

@Service
public class CollectionService {

    private final CollectionRepository collectionRepository;

    public CollectionService(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    public void addCollection(Collection collection) {
        collectionRepository.save(collection);
    }

    @Transactional
    public Collection getCollectionById(int collectionId) {
        return collectionRepository.findById(collectionId).orElse(null);
    }


    
}
