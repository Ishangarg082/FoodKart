package com.fods.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "restaurant_reviews", indexes = {
        @Index(name = "idx_review_rating", columnList = "rating"),
        @Index(name = "idx_review_restaurant", columnList = "restaurant_id")
})
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class RestaurantReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(nullable = false)
    private Long userId;  // Assuming users are managed in another microservice

    @Column(nullable = false)
    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String review;

    @CreationTimestamp
    private LocalDateTime createdAt;
}

