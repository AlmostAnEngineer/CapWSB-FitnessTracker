package com.capgemini.wsb.fitnesstracker.user.api;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserProvider {

    /**
     * Retrieves a user based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param userId id of the user to be searched
     * @return An {@link Optional} containing the located user, or {@link Optional#empty()} if not found
     */
    Optional<User> getUser(Long userId);
    List<User> findAllUsers();
    Optional<User> findUserById(Long userId);
    Collection<User> findUsersByEmail(String email);
    Collection<User> findUserOlderThan(LocalDate day);

}
