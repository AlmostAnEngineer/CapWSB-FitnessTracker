package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

interface TrainingRepository extends JpaRepository<Training, Long> {
    default List<Training> getTrainingsFinishedAfter(Date date)
    {
        return findAll().stream().filter(
                training -> Objects.compare(training.getEndTime(), date, Comparator.naturalOrder()) > 0
        ).collect(Collectors.toList());
    }
}
