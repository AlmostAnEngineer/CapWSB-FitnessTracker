package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingServiceImpl trainingService;
    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;

    private final UserService userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping
    public List<Training> getAllTrainings() {
        List<Training> allTrainings = trainingRepository.findAll();
        if(allTrainings.isEmpty()) {
            throw new RuntimeException("No trainings found");
        }
        return allTrainings;
    }

    @PostMapping
    public ResponseEntity<Training> addNewTraining(@RequestBody TrainingDtoWithUserId training) {
        if(training.userId() == null)
        {
            throw new RuntimeException("Training user id is null");
        }
        Optional<User> user = userRepository.findById(training.userId());
        if(user.isPresent()) {
            System.out.println("adding training:" + training);
            Training newTraining = trainingService.createTraining(trainingMapper.toEntity(training, user.get()));
            return new ResponseEntity<>(newTraining, HttpStatus.CREATED);
        }
        throw new RuntimeException("User not found");
    }

    @GetMapping("/finished/{afterTime}")
    public List<Training> getFinishedTrainings(@PathVariable ("afterTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date time) {
        return trainingRepository.getTrainingsFinishedAfter(time);
    }

    @GetMapping("/activityType")
    public List<Training> getTrainings(@RequestParam ("activityType") String activityType) {
        ActivityType activity = ActivityType.valueOf(activityType);
        return trainingRepository.getTrainingsByType(activity);
    }
}
