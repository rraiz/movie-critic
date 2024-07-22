package com.rraiz.movie_critic.feature.content_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rraiz.movie_critic.feature.content_management.model.Review;
import com.rraiz.movie_critic.feature.film.model.entity.Film;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findByFilmOrderByReviewDateDesc(Film film);
} 