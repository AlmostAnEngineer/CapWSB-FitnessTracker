package com.capgemini.wsb.fitnesstracker.statistics.internal;
import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.stereotype.Component;

@Component
public class StatisticsMapper {

    Statistics toEntity(StatisticsDto statistics, Long id, User user)
    {
        return new Statistics(id,
                user,
                statistics.totalTrainings(),
                statistics.totalDistance(),
                statistics.totalCaloriesBurned());
    }

    static StatisticsDto toDto(Statistics statistics)
    {
        return new StatisticsDto(
                statistics.getId(),
                statistics.getUser().getId(),
                statistics.getTotalTrainings(),
                statistics.getTotalDistance(),
                statistics.getTotalCaloriesBurned()
        );
    }
}
