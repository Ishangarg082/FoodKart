package com.fods.restaurant.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tags", indexes = {
        @Index(name = "idx_tag_name", columnList = "name")
})
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String name;
}
