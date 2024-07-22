package com.rraiz.movie_critic.feature.content_management.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rraiz.movie_critic.feature.film.model.entity.Film;
import com.rraiz.movie_critic.feature.security.model.ApplicationUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String review;

    private Double rating;

    private LocalDateTime reviewDate;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "film_id"),
        @JoinColumn(name = "film_type")
    })
    @JsonBackReference
    private Film film;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private ApplicationUser user;

    public Review() {
    }

    public Review(String review, Double rating, LocalDateTime reviewDate, Film film, ApplicationUser user) {
        this.review = review;
        this.rating = rating;
        this.reviewDate = reviewDate;
        this.film = film;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }    
    
}
