package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    @GetMapping("/simple")
    public List<UserSimpleDto> getAllUsersSimple() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toSimpleDto)
                          .toList();
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public Optional<UserDto> getAllUsersSimple(@PathVariable Long id) {
        Optional<User> user = Optional.ofNullable(userService.findUserById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
        return user.map(userMapper::toDto);
    }

    @DeleteMapping("/{id}")
    public Optional<UserDto> removeUser(@PathVariable Long id) {
        Optional<User> user =  userService.findUserById(id);
        if(user.isPresent())
        {
            userService.removeUser(user.get());
            return user.map(userMapper::toDto);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody UserDto userDto) {
        System.out.println("User with e-mail: " + userDto.email() + "passed to the request");
        User user = userService.createUser(userMapper.toEntity(userDto));
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public User patchUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        userService.getUser(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        return userService.patchUser(userMapper.toEntity(userDto, id));
    }

    @PostMapping("find/by-mail")
    public User findUserByMail(@RequestBody MailDto mail) {
        return userService.findUserByEmail(mail.email()).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @PostMapping("find/by-age")
    public Collection<User> findUserByMail(@RequestBody AgeDto mail) {
        Collection<User> foundUsers = userService.findUserOlderThan(mail.age());
        if(foundUsers.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        else
            return foundUsers;
    }

}