package com.rraiz.movie_critic.feature.content_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rraiz.movie_critic.feature.content_management.model.PopularFilms;

@Repository
public interface PopularFilmsRepository extends JpaRepository<PopularFilms, Integer> {

}

