package com.capgemini.wsb.fitnesstracker.statistics.internal;

import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    default List<Statistics> findAll() {
        return findAll().stream().sorted(Comparator.comparingLong(Statistics::getId)).collect(Collectors.toList());
    }

    default Optional<Statistics> findById(Long id) {
        return findById(id);
    }
}
