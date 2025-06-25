package com.fods.repository;

import com.fods.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, UUID> {
    UserDetails findByEmailIdAndPhoneNumber(String emailId, String phoneNumber);
}
