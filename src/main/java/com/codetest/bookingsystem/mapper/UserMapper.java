package com.codetest.bookingsystem.mapper;

import com.codetest.bookingsystem.dto.UserDTO;
import com.codetest.bookingsystem.model.User;

public class UserMapper {

    public static UserDTO mapToUserDTO(User user) {
        UserDTO userDTO = null;
        if (user != null) {
            userDTO = new UserDTO(user.getId(), user.getUsername(), user.getEmail(),
                    user.getPassword(), user.getPhoneNo(),
                    user.getAddress(), user.getRole(), user.getStatus());
        }
        return userDTO;
    }

    public static User mapToUser(UserDTO userDTO) {
        User user = null;
        if (userDTO != null) {
            user = new User(userDTO.getId(), userDTO.getUserName(), userDTO.getEmail(),
                    userDTO.getPassword(), userDTO.getPhoneNo(),
                    userDTO.getAddress(), userDTO.getRole(), userDTO.getStatus());
        }
        return user;
    }
}
