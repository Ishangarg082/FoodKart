package com.fods.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Entity
@Table(name = "restaurants", indexes = {
        @Index(name = "idx_restaurant_name", columnList = "name"),
        @Index(name = "idx_avg_rating", columnList = "averageRating")
})
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String phone;
    private String email;
    private String website;

    private LocalTime openingTime;
    private LocalTime closingTime;

    private Boolean isOpen = true;

    private Float averageRating = 0f;
    private Integer totalReviews = 0;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // One-to-One or One-to-Many if supporting multiple branches
    @OneToOne(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private RestaurantLocation location;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<MenuItem> menuItems = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<RestaurantReview> reviews = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "restaurant_tags",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();
}
