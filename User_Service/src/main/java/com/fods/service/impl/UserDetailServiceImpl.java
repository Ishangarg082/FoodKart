package com.fods.service.impl;

import com.fods.entity.UserDetails;
import com.fods.exception.InternalServerErrorException;
import com.fods.exception.UserNotFoundException;
import com.fods.mapper.UserDetailsServiceMapper;
import com.fods.model.UserDetailResponseDTO;
import com.fods.model.UserDetailsRequestDTO;
import com.fods.repository.UserDetailsRepository;
import com.fods.service.UserDetailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailService {
    private final UserDetailsRepository userDetailsRepository;
    private static final Logger logger = LogManager.getLogger(UserDetailServiceImpl.class);

    public UserDetailServiceImpl(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public UserDetailResponseDTO saveUserDetails(UserDetailsRequestDTO userDetailsRequestDTO) {
        logger.info("Entering inside saveUserDetails...........................");
        UserDetails userDetailsEntityObj = UserDetailsServiceMapper.getUserDetailsEntityObj(userDetailsRequestDTO);
        UserDetails userDetails = userDetailsRepository.save(userDetailsEntityObj);
        return UserDetailsServiceMapper.toUserDetailsResponseDTO(userDetails);
    }

    @Override
    public UserDetailResponseDTO updateUserDetails(UserDetailsRequestDTO userDetailsRequestDTO) {
        try {
            logger.info("Entering inside updateUserDetails...........................");
            String contactNumber = userDetailsRequestDTO.contactNumber();
            String emailId = userDetailsRequestDTO.emailId();
            logger.info("User Contact Number: {} || Email_ID: {}", contactNumber, emailId);
            if (contactNumber == null || contactNumber.isEmpty() || emailId == null || emailId.isEmpty()) {
                throw new IllegalArgumentException("Email ID and Contact Number must not be empty");
            }
            UserDetails userDetails = userDetailsRepository.findByEmailIdAndPhoneNumber(emailId,
                    contactNumber);
            if (userDetails == null) {
                throw new UserNotFoundException("User not found with given email and contact number");
            }
            UserDetailsServiceMapper.updateExistingUserDetailsMapper(userDetails, userDetailsRequestDTO);
            UserDetails updatedUserDetails = userDetailsRepository.save(userDetails);
            return UserDetailsServiceMapper.toUserDetailsResponseDTO(userDetails);

        } catch (IllegalArgumentException | UserNotFoundException e) {
            // Custom exception for validation or business logic failure
            throw e;
        } catch (Exception e) {
            // Catch-all for unexpected exceptions
            throw new InternalServerErrorException("Failed to update user details", e);
        }
    }

}
