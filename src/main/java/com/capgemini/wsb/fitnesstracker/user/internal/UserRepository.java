package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Query searching users by email address. It matches by exact match.
     *
     * @param email email of the user to search
     * @return {@link Optional} containing found user or {@link Optional#empty()} if none matched
     */
    default List<User> findByEmail(String email) {
        return findAll().stream()
                        .filter(user -> user.getEmail().contains(email))
                        .collect(Collectors.toList());
    }

    default Collection<User> findOlderThan(LocalDate day) {
        return findAll().stream()
                .filter(user -> Objects.compare(user.getBirthdate(), day, Comparator.naturalOrder()) < 0)
                .collect(Collectors.toList());
    }
}
