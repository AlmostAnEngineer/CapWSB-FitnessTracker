package com.capgemini.wsb.fitnesstracker.training.internal;


import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Null;
import com.capgemini.wsb.fitnesstracker.user.api.User;

import java.util.Date;

record TrainingDto(
        @Nullable Long id,
        User user,
        Date startTime,
        Date endTime,
        ActivityType activityType,
        double distance,
        double averageSpeed
){}

record TrainingDtoWithUserId(
        @Nullable Long id,
        Long userId,
        Date startTime,
        Date endTime,
        ActivityType activityType,
        double distance,
        double averageSpeed
){}
