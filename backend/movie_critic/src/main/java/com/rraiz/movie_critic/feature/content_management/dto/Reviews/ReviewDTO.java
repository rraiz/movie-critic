package com.rraiz.movie_critic.feature.content_management.dto.Reviews;

import java.time.LocalDateTime;

import com.rraiz.movie_critic.feature.content_management.model.Review;

public class ReviewDTO {


    private int reviewId;
    private String reviewText;
    private double rating;
    private LocalDateTime reviewDate;
    private String username;

    public ReviewDTO() {
    }

    public ReviewDTO(Review review) {
        this.reviewId = review.getId();
        this.reviewText = review.getReview();
        this.rating = review.getRating();
        this.reviewDate = review.getReviewDate();
        this.username = review.getUser().getUsername();
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
