package com.capgemini.wsb.fitnesstracker.statistics.api;

import com.capgemini.wsb.fitnesstracker.exception.api.NotFoundException;

public class StatisticsNotFoundException extends NotFoundException {

    private StatisticsNotFoundException(String message) {
        super(message);
    }

    public StatisticsNotFoundException(Long id) {
        this("Statistics with ID=%s was not found".formatted(id));
    }

}
