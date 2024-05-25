package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final UserRepository userRepository;

    public UserMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    UserDto toDto(User user) {
        return new UserDto(user.getId(),
                           user.getFirstName(),
                           user.getLastName(),
                           user.getBirthdate(),
                           user.getEmail());
    }

    User toEntity(UserDto userDto) {
        return new User(
                        userDto.firstName(),
                        userDto.lastName(),
                        userDto.birthdate(),
                        userDto.email());
    }

    User toEntity(UserDto userDto, Long id) {
        return new User(
                id,
                userDto.firstName(),
                userDto.lastName(),
                userDto.birthdate(),
                userDto.email());
    }

    UserSimpleDto toSimpleDto(User user) {
        return new UserSimpleDto(user.getId(), user.getFirstName(), user.getLastName());
    }

    MailDto toMailDto(User user) {
        return new MailDto(user.getId(), user.getEmail());
    }

    User toEntity(MailDto mailDto) {
        User user = userRepository.findById(mailDto.id()).orElseThrow(()
                -> new UserNotFoundException(mailDto.id()));
        return user;
    }
}
