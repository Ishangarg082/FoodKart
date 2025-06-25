package com.fods.repository;

import com.fods.entity.CartItemDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartItemDetailRepository extends JpaRepository<CartItemDetail, UUID> {
}
