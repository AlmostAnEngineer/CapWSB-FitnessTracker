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
    public ResponseEntity<User> addUser(@RequestBody UserDto userDto) {
        System.out.println("User with e-mail: " + userDto.email() + "passed to the request");
        User user = userService.createUser(userMapper.toEntity(userDto));
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public User patchUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        userService.getUser(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        return userService.patchUser(userMapper.toEntity(userDto, id));
    }

    @GetMapping("/email")
    public Collection<MailDto> findUsersOlderThan(@RequestParam String email) {
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
    public Collection<User> findUsersOlderThan(@PathVariable("time") LocalDate time) {
        Collection<User> foundUsers = userService.findUserOlderThan(time);
        if(foundUsers.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        else
            return foundUsers;
    }
}