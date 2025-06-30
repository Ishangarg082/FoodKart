package com.fods.mapper;

import com.fods.entity.UserAddressDetails;
import com.fods.entity.UserDetails;
import com.fods.model.UserAddressDTO;
import com.fods.model.UserDetailResponseDTO;
import com.fods.model.UserDetailsRequestDTO;

import java.util.List;

public class UserDetailsServiceMapper {

    public static UserAddressDetails getUserAddressDetails(UserAddressDTO userAddressDTO) {
        return UserAddressDetails
                .builder()
                .state(userAddressDTO.state())
                .city(userAddressDTO.city())
                .street(userAddressDTO.street())
                .country("India")
                .buildingName(userAddressDTO.buildingName())
                .pinCode(String.valueOf(userAddressDTO.pinCode()))
                .build();
    }

    public static UserDetails getUserDetailsEntityObj(UserDetailsRequestDTO userDetailsRequestDTO) {
        UserAddressDetails userAddressDetails = getUserAddressDetails(userDetailsRequestDTO.addressDetails());

        UserDetails userDetails = UserDetails
                .builder()
                .userName(userDetailsRequestDTO.userName())
                .emailId(userDetailsRequestDTO.emailId())
                .phoneNumber(userDetailsRequestDTO.contactNumber())
                .password(userDetailsRequestDTO.password())
                .build();

        userAddressDetails.setUserDetails(userDetails);
        userDetails.setAddresses(List.of(userAddressDetails));

        return userDetails;
    }

    public static void updateExistingUserDetailsMapper(UserDetails userDetails, UserDetailsRequestDTO userDetailsRequestDTO) {
        userDetails.setUserName(userDetails.getUserName());
        userDetails.setEmailId(userDetails.getEmailId());
        userDetails.setPhoneNumber(userDetails.getPhoneNumber());
        userDetails.setPassword(userDetails.getPassword());
        if(userDetailsRequestDTO.addressDetails() != null) {
            List<UserAddressDetails> addressDetailsList = userDetails.getAddresses();
            if(addressDetailsList.isEmpty()) {
                UserAddressDetails userAddressDetails = getUserAddressDetails(userDetailsRequestDTO.addressDetails());
                addressDetailsList.add(userAddressDetails);
            } else {
                UserAddressDetails userAddressDetails = addressDetailsList.getFirst();
                updateExistingAddressDetails(userAddressDetails, userDetailsRequestDTO.addressDetails());
            }
        }
    }

    public static void updateExistingAddressDetails(UserAddressDetails userAddressDetails, UserAddressDTO userAddressDTO) {
        userAddressDetails.setState(userAddressDTO.state());
        userAddressDetails.setCity(userAddressDTO.city());
        userAddressDetails.setCountry("India");
        userAddressDetails.setStreet(userAddressDTO.street());
        userAddressDetails.setBuildingName(userAddressDTO.buildingName());
        userAddressDetails.setPinCode(userAddressDetails.getPinCode());
    }

    public static UserDetailResponseDTO toUserDetailsResponseDTO(UserDetails userDetails) {
        return new UserDetailResponseDTO(
                userDetails.getUserId(),
                userDetails.getUserName(),
                userDetails.getPhoneNumber()
        );
    }


}
