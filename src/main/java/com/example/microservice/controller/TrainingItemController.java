package com.example.microservice.controller;

import com.example.microservice.dto.TrainerWorkloadDTO;
import com.example.microservice.entity.enums.ActionType;
import com.example.microservice.service.TrainingItemService;
import com.example.microservice.service.TrainingItemSummaryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/training_item")
@AllArgsConstructor
public class TrainingItemController {

    private TrainingItemService trainingItemService;

    private TrainingItemSummaryService trainingItemSummaryService;

    @PostMapping
    public void updateTrainingItem(@RequestParam(value = "trainerUserName") final String trainerUserName,
                                   @RequestParam(value = "trainerFirstName") final String trainerFirstName,
                                   @RequestParam(value = "trainerLastName") final String trainerLastName,
                                   @RequestParam(value = "isActive") final boolean isActive,
                                   @RequestParam(value = "trainingDate") final LocalDate trainingDate,
                                   @RequestParam(value = "trainingDuration") final Long trainingDuration,
                                   @RequestParam(value = "actionType") final ActionType actionType) {
        trainingItemService.updateTrainingItem(trainerUserName, trainerFirstName, trainerLastName, isActive, trainingDate, trainingDuration, actionType);
    }

    @GetMapping("/trainer")
    public TrainerWorkloadDTO getTrainerWorkload(@RequestParam(value = "trainerUserName") final String trainerUserName) {
        return trainingItemSummaryService.getTrainerWorkload(trainerUserName);
    }

}
