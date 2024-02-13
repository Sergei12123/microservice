package com.example.microservice.service;

import com.example.microservice.entity.enums.ActionType;

import java.time.LocalDate;

public interface TrainingItemService {

    /**
     * Updates a training item with the given information.
     *
     * @param trainerUserName  the username of the trainer
     * @param trainerFirstName the first name of the trainer
     * @param trainerLastName  the last name of the trainer
     * @param isActive         the active status of the training item
     * @param trainingDate     the date of the training
     * @param trainingDuration the duration of the training
     * @param actionType       the type of action to be performed
     */
    void updateTrainingItem(String trainerUserName,
                            String trainerFirstName,
                            String trainerLastName,
                            boolean isActive,
                            LocalDate trainingDate,
                            Long trainingDuration,
                            ActionType actionType);
}
