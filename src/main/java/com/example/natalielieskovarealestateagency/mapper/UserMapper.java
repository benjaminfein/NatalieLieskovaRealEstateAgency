package com.example.natalielieskovarealestateagency.mapper;

import com.example.natalielieskovarealestateagency.dto.user.CreateUserDTO;
import com.example.natalielieskovarealestateagency.dto.user.UserDTO;
import com.example.natalielieskovarealestateagency.model.User;

public class UserMapper {
    public static UserDTO mapToUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole(),
                user.getName(),
                user.getPassword(),
                String.valueOf(user.getEmailConfirmed())
        );
    }

    public static User mapToUser(UserDTO userDTO) {
        return new User(
                userDTO.getId(),
                userDTO.getUsername(),
                userDTO.getEmail(),
                userDTO.getPhoneNumber(),
                userDTO.getRole(),
                userDTO.getName(),
                userDTO.getPassword()
        );
    }

    public static CreateUserDTO mapToCreateUserDTO(User user) {
        return new CreateUserDTO(
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole(),
                user.getName(),
                user.getPassword(),
                user.getLang()
        );
    }

    public static User mapToUser(CreateUserDTO createUserDTO) {
        return new User(
                createUserDTO.getUsername(),
                createUserDTO.getEmail(),
                createUserDTO.getPhoneNumber(),
                createUserDTO.getRole(),
                createUserDTO.getName(),
                createUserDTO.getPassword(),
                createUserDTO.getLang()
        );
    }
}
