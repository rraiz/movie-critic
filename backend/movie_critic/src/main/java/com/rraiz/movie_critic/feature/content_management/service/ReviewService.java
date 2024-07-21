package com.rraiz.movie_critic.feature.content_management.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rraiz.movie_critic.feature.content_management.dto.Reviews.ReviewDTO;
import com.rraiz.movie_critic.feature.content_management.model.Review;
import com.rraiz.movie_critic.feature.content_management.repository.ReviewRepository;
import com.rraiz.movie_critic.feature.film.model.identifier.FilmId;
import com.rraiz.movie_critic.feature.film.repository.FilmRepository;
import com.rraiz.movie_critic.feature.security.model.ApplicationUser;
import com.rraiz.movie_critic.feature.security.service.UserService;

import jakarta.servlet.http.HttpServletResponse;

import com.rraiz.movie_critic.feature.film.model.entity.Film;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final FilmRepository filmRepository;
    private final UserService userService;


    public ReviewService(ReviewRepository reviewRepository, FilmRepository filmRepository, UserService userService) {
        this.reviewRepository = reviewRepository;
        this.filmRepository = filmRepository;
        this.userService = userService;
    }

    public List<ReviewDTO> getFilmReviews(int filmId, int filmType) {

        FilmId filmIdentifier = new FilmId(filmId, filmType);
        Optional<Film> film = filmRepository.findById(filmIdentifier);
        if (film.isEmpty()) {
            return null;
        } else {
            List<Review> reviews = reviewRepository.findByFilm(film.get());
            List<ReviewDTO> reviewDTOs = new ArrayList<ReviewDTO>();
            for (Review review : reviews) {
                reviewDTOs.add(new ReviewDTO(review));
            }
            return reviewDTOs;
        }
    }

    public String addReview(int filmId, int filmType, ReviewDTO reviewDTO, HttpServletResponse response) {
        try {
            String username =  userService.getUsername();
            ApplicationUser user = (ApplicationUser) userService.loadUserByUsername(username);

            FilmId filmIdentifier = new FilmId(filmId, filmType);
            Optional<Film> film = filmRepository.findById(filmIdentifier);
            if (film.isEmpty()) {
                throw new Exception("Film not found");
            }

            Review review = new Review(reviewDTO.getReviewText(), reviewDTO.getRating(), LocalDateTime.now(), film.get(), user);
            reviewRepository.save(review);

            return "Review added";
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return  e.getMessage();
        }

    }
}
