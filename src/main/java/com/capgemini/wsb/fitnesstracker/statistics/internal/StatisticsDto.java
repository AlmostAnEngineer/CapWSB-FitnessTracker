package com.capgemini.wsb.fitnesstracker.statistics.internal;
import jakarta.annotation.Nullable;

record StatisticsDto(
        @Nullable Long id,
        int totalTrainings,
        double totalDistance,
        int totalCaloriesBurned
        ){}