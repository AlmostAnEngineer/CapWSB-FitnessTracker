package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;

public class TrainingMapper {
    Training toEntity(TrainingDto trainingDto, Long id){
        return new Training(id, trainingDto.user(), trainingDto.startTime(),
                trainingDto.endTime(), trainingDto.activityType(),
                trainingDto.distance(), trainingDto.averageSpeed()
        );
    }

    TrainingDto toDto(Training training){
        return new TrainingDto(
                training.getId(), training.getUser(), training.getStartTime(),
                training.getEndTime(), training.getActivityType(), training.getDistance(),
                training.getAverageSpeed()
        );
    }
}
