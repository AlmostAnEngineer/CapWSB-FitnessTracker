package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
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
    public ResponseEntity<List<TrainingDtoWithUserDto>> getAllTrainings() {
        List<Training> allTrainings = trainingRepository.findAll();
        List<TrainingDtoWithUserDto> userDtos = new ArrayList<>();
        if (!allTrainings.isEmpty()) {
            for (Training training : allTrainings) {
                userDtos.add(trainingMapper.toDtoWithUserDto(training));
            }
            return new ResponseEntity<>(userDtos, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<TrainingDtoWithUserDto> addNewTraining(@RequestBody TrainingDtoWithUserId training) {
        if(training.userId() == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<User> user = userRepository.findById(training.userId());
        if(user.isPresent()) {
            System.out.println("adding training:" + training);
            Training newTraining = trainingService.createTraining(trainingMapper.toEntity(training, user.get()));
            return new ResponseEntity<>(trainingMapper.toDtoWithUserDto(newTraining), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/finished/{afterTime}")
    public ResponseEntity<List<TrainingDtoWithUserDto>> getFinishedTrainings(@PathVariable ("afterTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date time) {
        List<TrainingDtoWithUserDto> trainings = trainingRepository.getTrainingsFinishedAfter(time).stream().map(TrainingMapper::toDtoWithUserDto).toList();
        if(trainings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(trainings, HttpStatus.OK);
    }

    @GetMapping("/activityType")
    public ResponseEntity<List<Training>> getTrainings(@RequestParam ("activityType") String activityType) {
        ActivityType activity = ActivityType.valueOf(activityType);
        if(activity == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(trainingRepository.getTrainingsByType(activity), HttpStatus.OK);
    }

    @PutMapping("/{trainingId}")
    public ResponseEntity<TrainingDtoWithUserDto> updateTraining(@PathVariable("trainingId") Long id, @RequestBody TrainingDtoForPut training) {
        Optional<Training> actTraining = trainingService.getTrainingById(id);
        if (actTraining.isPresent()) {
            User actualUser = actTraining.get().getUser();
            Training newTraining = trainingMapper.toEntity(id, training, actTraining.get(), actualUser);
            trainingRepository.updateTraining(newTraining);
            return new ResponseEntity<>(TrainingMapper.toDtoWithUserDto(newTraining), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<TrainingDtoWithUserDto>> getTrainingsById(@PathVariable("userId") Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            User actualUser = user.get();
            List<Training> trainings = trainingService.getTrainingsByUserId(actualUser.getId());
            List<TrainingDtoWithUserDto> mappedTrainings = trainings.stream().map(TrainingMapper::toDtoWithUserDto).toList();
            return new ResponseEntity<>(mappedTrainings, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
