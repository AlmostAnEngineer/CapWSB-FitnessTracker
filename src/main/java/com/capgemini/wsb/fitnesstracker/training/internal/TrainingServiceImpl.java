package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingServiceImpl implements TrainingProvider, TrainingService {
    private final TrainingRepository trainingRepository;

    @Override
    public Optional<User> getAllTrainings(final Long trainingId) {
        throw new UnsupportedOperationException("Not finished yet");
    }

    @Override
    public Training createTraining(Training training)
    {
        log.info("Creating training {}", training);
        if (training.getId() != null) {
            throw new IllegalArgumentException("Training id is already set");
        }
        return trainingRepository.save(training);
    }

    @Override
    public List<Training> getTrainingsFinishedAfter(Date dateTime)
    {
        return trainingRepository.getTrainingsFinishedAfter(dateTime);
    }

}
