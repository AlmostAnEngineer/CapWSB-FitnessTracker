package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
import org.apache.catalina.mbeans.UserMBean;
import org.springframework.stereotype.Component;

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
}
