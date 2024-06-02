package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @GetMapping("/simple")
    public List<UserSimpleDto> getSimpleUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toSimpleDto)
                          .toList();
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(value -> new ResponseEntity<>(UserMapper.toDto(value), HttpStatus.OK)).orElseGet(
                () -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> removeUser(@PathVariable Long id) {
        Optional<User> user =  userService.findUserById(id);
        if(user.isPresent())
        {
            userService.removeUser(user.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        User user = userService.createUser(userMapper.toEntity(userDto));
        return new ResponseEntity<>(UserMapper.toDto(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> patchUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        Optional<User> actualUser = userService.findUserById(id);
        if(actualUser.isPresent()) {
            User user = userService.patchUser(userMapper.toEntity(userDto, id));
            return new ResponseEntity<>(UserMapper.toDto(user), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/email")
    public Collection<MailDto> findUsersByEmail(@RequestParam String email) {
        Collection<User> users = userService.findUsersByEmail(email);
        Collection<MailDto> output = new ArrayList<>();
        for (User user : users) {
            output.add(userMapper.toMailDto(user));
        }
        if(output.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        else
        {
            return output;
        }
    }

    @GetMapping("/older/{time}")
    public ResponseEntity<Collection<UserDto>> findUsersOlderThan(@PathVariable("time") LocalDate time) {
        Collection<User> foundUsers = userService.findUserOlderThan(time);
        if(foundUsers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            List<UserDto> output = foundUsers.stream().map(UserMapper::toDto).toList();
            return new ResponseEntity<>(output, HttpStatus.OK);
        }
    }
}