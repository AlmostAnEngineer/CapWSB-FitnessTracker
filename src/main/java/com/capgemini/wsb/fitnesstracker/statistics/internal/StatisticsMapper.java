package com.capgemini.wsb.fitnesstracker.statistics.internal;
import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import com.capgemini.wsb.fitnesstracker.user.api.User;

public class StatisticsMapper {
    Statistics toEntity(StatisticsDto statistics, Long id)
    {
        return new Statistics(id,
                statistics.user(),
                statistics.totalTrainings(),
                statistics.totalDistance(),
                statistics.totalCaloriesBurned());
    }

    Statistics toEntity(StatisticsDto statistics, Long id, User user)
    {
        return new Statistics(id,
                user,
                statistics.totalTrainings(),
                statistics.totalDistance(),
                statistics.totalCaloriesBurned());
    }

    Statistics toEntity(StatisticsDto statistics)
    {
        return new Statistics(statistics.id(),
                statistics.user(),
                statistics.totalTrainings(),
                statistics.totalDistance(),
                statistics.totalCaloriesBurned());
    }

    StatisticsDto toDto(Statistics statistics)
    {
        return new StatisticsDto(
                statistics.getId(),
                statistics.getUser(),
                statistics.getTotalTrainings(),
                statistics.getTotalDistance(),
                statistics.getTotalCaloriesBurned()
        );
    }
}
