package com.example.microservice.service.impl;

import com.example.microservice.dto.TrainerWorkloadDTO;
import com.example.microservice.entity.TrainingItem;
import com.example.microservice.entity.enums.ActionType;
import com.example.microservice.repository.TrainingItemRepository;
import com.example.microservice.repository.TrainingItemSummaryRepository;
import com.example.microservice.service.TrainingItemService;
import com.example.microservice.service.TrainingItemSummaryService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainingItemServiceImpl implements TrainingItemService {

    private final TrainingItemRepository trainingItemRepository;

    private final TrainingItemSummaryService trainingItemSummaryService;

    public TrainingItemServiceImpl(TrainingItemRepository trainingItemRepository, TrainingItemSummaryService trainingItemSummaryService) {
        this.trainingItemRepository = trainingItemRepository;
        this.trainingItemSummaryService = trainingItemSummaryService;
    }

    @Override
    public void updateTrainingItem(String trainerUserName,
                                   String trainerFirstName,
                                   String trainerLastName,
                                   boolean isActive,
                                   LocalDate trainingDate,
                                   Long trainingDuration,
                                   ActionType actionType) {
        if (actionType == ActionType.ADD) {
            trainingItemRepository.save(TrainingItem.builder()
                .trainerUserName(trainerUserName)
                .trainerFirstName(trainerFirstName)
                .trainerLastName(trainerLastName)
                .isActive(isActive)
                .trainingDate(trainingDate)
                .trainingDuration(trainingDuration)
                .build());

        } else if (actionType == ActionType.DELETE) {
            trainingItemRepository.deleteByTrainerUserNameAndTrainerFirstNameAndTrainerLastNameAndIsActiveAndTrainingDateAndTrainingDuration(
                trainerUserName,
                trainerFirstName,
                trainerLastName,
                isActive,
                trainingDate,
                trainingDuration);
        }
        trainingItemSummaryService.updateTrainingItemSummary(trainerUserName, trainerFirstName, trainerLastName, isActive, trainingDate, trainingDuration, actionType);
    }


}
