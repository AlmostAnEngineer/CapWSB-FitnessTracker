package com.capgemini.wsb.fitnesstracker.statistics.internal;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserDto;
import jakarta.annotation.Nullable;

record StatisticsDto(
        @Nullable Long id,
        Long userid,
        int totalTrainings,
        double totalDistance,
        int totalCaloriesBurned
        ){}

record StatisticsDtWithUserDto(
        @Nullable Long id,
        UserDto user,
        int totalTrainings,
        double totalDistance,
        int totalCaloriesBurned
){}