package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
import org.apache.catalina.mbeans.UserMBean;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
public class TrainingMapper {
    Training toEntity(TrainingDtoWithUserId trainingDto, User user){
        if(trainingDto.id() == null) {
            return new Training(user, trainingDto.startTime(),
                    trainingDto.endTime(), trainingDto.activityType(),
                    trainingDto.distance(), trainingDto.averageSpeed()
            );
        }
        else{
            return new Training(trainingDto.id(), user, trainingDto.startTime(),
                    trainingDto.endTime(), trainingDto.activityType(),
                    trainingDto.distance(), trainingDto.averageSpeed()
            );
        }
    }

    TrainingDto toDto(Training training){
        return new TrainingDto(
                training.getId(), training.getUser(), training.getStartTime(),
                training.getEndTime(), training.getActivityType(), training.getDistance(),
                training.getAverageSpeed()
        );
    }

    Training toEntity(TrainingDto trainingDto){
        if(trainingDto.id() == null) {
            return new Training(trainingDto.user(), trainingDto.startTime(),
                    trainingDto.endTime(), trainingDto.activityType(),
                    trainingDto.distance(), trainingDto.averageSpeed()
            );
        }
        else
        {
            return new Training(trainingDto.id(), trainingDto.user(), trainingDto.startTime(),
                    trainingDto.endTime(), trainingDto.activityType(),
                    trainingDto.distance(), trainingDto.averageSpeed()
            );
        }
    }

    static TrainingDtoWithUserDto toDtoWithUserDto(Training training){
        return new TrainingDtoWithUserDto(
                training.getId(), UserMapper.toDto(training.getUser()), training.getStartTime(),
                training.getEndTime(), training.getActivityType(),
                training.getDistance(), training.getAverageSpeed()
        );
    }

    Training toEntity(TrainingDto trainingDto, Long id){
            return new Training(id, trainingDto.user(), trainingDto.startTime(),
                    trainingDto.endTime(), trainingDto.activityType(),
                    trainingDto.distance(), trainingDto.averageSpeed()
            );
    }

    Training toEntity(TrainingDto trainingDto, Long id, User user){
        return new Training(id, user, trainingDto.startTime(),
                trainingDto.endTime(), trainingDto.activityType(),
                trainingDto.distance(), trainingDto.averageSpeed()
        );
    }

    public Training toEntity(Long id, TrainingDtoForPut newTraining, Training trainingDto, User user) {
        Date startTime = (newTraining.startTime() != null && !Objects.equals(trainingDto.getStartTime(), newTraining.startTime()))
                ? newTraining.startTime()
                : trainingDto.getStartTime();

        Date endTime = (newTraining.endTime() != null && !Objects.equals(trainingDto.getEndTime(), newTraining.endTime()))
                ? newTraining.endTime()
                : trainingDto.getEndTime();

        ActivityType activityType = (newTraining.activityType() != null && !Objects.equals(trainingDto.getActivityType(), newTraining.activityType()))
                ? newTraining.activityType()
                : trainingDto.getActivityType();

        Double distance = (newTraining.distance() != null && !Objects.equals(trainingDto.getDistance(), newTraining.distance()))
                ? newTraining.distance()
                : trainingDto.getDistance();

        Double avgSpeed = (newTraining.averageSpeed() != null && !Objects.equals(trainingDto.getAverageSpeed(), newTraining.averageSpeed()))
                ? newTraining.averageSpeed()
                : trainingDto.getAverageSpeed();

        return new Training(id, user, startTime, endTime, activityType, distance, avgSpeed);
    }
}
