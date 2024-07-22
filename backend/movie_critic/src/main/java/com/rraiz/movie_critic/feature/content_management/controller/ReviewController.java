package com.rraiz.movie_critic.feature.content_management.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rraiz.movie_critic.feature.content_management.dto.Reviews.ReviewDTO;
import com.rraiz.movie_critic.feature.content_management.service.ReviewService;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/v1/review")
@CrossOrigin(origins = "http://localhost:5173")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{filmType}/{filmId}/reviews")
    public List<ReviewDTO> getFilmReviews(@PathVariable int filmId, @PathVariable int filmType, HttpServletResponse response) {
        return reviewService.getFilmReviews(filmId, filmType);
    }

    @PostMapping("/{filmType}/{filmId}/addReview")
    public String addReview(HttpServletResponse response, @PathVariable int filmId, @PathVariable int filmType, @RequestBody ReviewDTO reviewDTO) {
        return reviewService.addReview(filmId, filmType, reviewDTO, response);
    }

    @PutMapping("/updateReview/{reviewId}")
    public String updateReview(@PathVariable int reviewId, @RequestBody ReviewDTO reviewDTO) {
        return reviewService.updateReview(reviewId, reviewDTO);
    
    }

    @DeleteMapping("/deleteReview/{reviewId}")
    public String deleteReview(@PathVariable int reviewId) {
        return reviewService.deleteReview(reviewId);
    }


}
