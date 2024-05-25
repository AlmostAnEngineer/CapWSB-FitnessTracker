package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingServiceImpl trainingService;
    private final TrainingRepository trainingRepository;

    @GetMapping
    public List<Training> getAllTrainings() {
        List<Training> allTrainings = trainingRepository.findAll();
        if(allTrainings.isEmpty()) {
            throw new RuntimeException("No trainings found");
        }
        return allTrainings;
    }

    @PostMapping
    public List<Training> addNewTraining(@RequestBody Training training) {
        List<Training> allTrainings = trainingRepository.findAll();
        if(allTrainings.isEmpty()) {
            throw new RuntimeException("No trainings found");
        }
        return allTrainings;
    }
}
