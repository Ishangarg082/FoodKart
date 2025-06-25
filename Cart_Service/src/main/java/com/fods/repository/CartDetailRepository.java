package com.fods.repository;

import com.fods.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, UUID> {
    Optional<CartDetail> findByUserId(UUID userId);
}
