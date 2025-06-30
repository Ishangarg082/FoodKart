package com.fods.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user_details", indexes = {
        @Index(name="idx_user_name", columnList = "user_name"),
        @Index(name = "idx_email_id", columnList = "email_id"),
        @Index(name = "idx_phone_number", columnList = "phone_number")
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDetails {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email_id")
    private String emailId;

    private String password;

    @OneToMany(mappedBy = "userDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAddressDetails> addresses = new ArrayList<>();
}