package com.rraiz.movie_critic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rraiz.movie_critic.model.TvShowNetwork;
import com.rraiz.movie_critic.model.TvShowNetworkId;

@Repository
public interface TvShowNetworkRepository extends JpaRepository<TvShowNetwork, TvShowNetworkId>{
    
}
