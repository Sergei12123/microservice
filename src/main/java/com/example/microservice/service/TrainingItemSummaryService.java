package com.example.microservice.service;

import com.example.microservice.dto.TrainerWorkloadDTO;
import com.example.microservice.entity.enums.ActionType;

import java.time.LocalDate;

public interface TrainingItemSummaryService {

    /**
     * Updates the training item summary based on the specified action type.
     *
     * @param trainerUserName   the username of the trainer
     * @param trainerFirstName  the first name of the trainer
     * @param trainerLastName   the last name of the trainer
     * @param isActive          whether the trainer is active
     * @param trainingDate      the date of the training
     * @param trainingDuration  the duration of the training
     * @param actionType        the type of action to be performed
     */
    void updateTrainingItemSummary(String trainerUserName,
                                   String trainerFirstName,
                                   String trainerLastName,
                                   boolean isActive,
                                   LocalDate trainingDate,
                                   Long trainingDuration,
                                   ActionType actionType);

    /**
     * Retrieves the workload for a specific trainer.
     *
     * @param  trainerUserName   the username of the trainer
     * @return                   the TrainerWorkloadDTO object
     */
    TrainerWorkloadDTO getTrainerWorkload(String trainerUserName);
}
