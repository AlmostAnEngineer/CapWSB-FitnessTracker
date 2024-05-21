package com.capgemini.wsb.fitnesstracker.user.api;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nullable
    private Long id;

    @Setter
    @Getter
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Setter
    @Getter
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Setter
    @Getter
    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    @Getter
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Training> trainings;

    public User(
            final String firstName,
            final String lastName,
            final LocalDate birthdate,
            final String email) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.email = email;
        this.trainings = new ArrayList<>();
    }

    public User(
            @Nullable final Long id,
            final String firstName,
            final String lastName,
            final LocalDate birthdate,
            final String email) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.email = email;
        this.trainings = new ArrayList<>();
    }

    @Nullable
    public Long getId() {
        return id;
    }

    public Integer getAge()
    {
        return LocalDate.now().getYear() - birthdate.getYear();
    }

}

