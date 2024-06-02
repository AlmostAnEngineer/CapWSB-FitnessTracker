package com.capgemini.wsb.fitnesstracker.statistics.internal;
import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class StatisticsMapper {

    static Statistics toEntity(StatisticsDto statistics, Long id, User user)
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

    static StatisticsDtWithUserDto toDtowithUserDto(Statistics statistics, User user) {
        return new StatisticsDtWithUserDto(statistics.getId(),
                UserMapper.toDto(user),
                statistics.getTotalTrainings(),
                statistics.getTotalDistance(),
                statistics.getTotalCaloriesBurned());
    }
}
