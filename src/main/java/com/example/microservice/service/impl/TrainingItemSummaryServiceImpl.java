package com.example.microservice.service.impl;

import com.example.microservice.dto.TrainerWorkloadDTO;
import com.example.microservice.entity.TrainingItemSummary;
import com.example.microservice.entity.enums.ActionType;
import com.example.microservice.repository.TrainingItemSummaryRepository;
import com.example.microservice.service.TrainingItemSummaryService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class TrainingItemSummaryServiceImpl implements TrainingItemSummaryService {


    private final TrainingItemSummaryRepository trainingItemSummaryRepository;


    public TrainingItemSummaryServiceImpl(TrainingItemSummaryRepository trainingItemSummaryRepository) {
        this.trainingItemSummaryRepository = trainingItemSummaryRepository;
    }

    @Override
    public void updateTrainingItemSummary(String trainerUserName,
                                          String trainerFirstName,
                                          String trainerLastName,
                                          boolean isActive,
                                          LocalDate trainingDate,
                                          Long trainingDuration,
                                          ActionType actionType) {
        if (actionType == ActionType.ADD) {
            List<TrainingItemSummary> allByTrainerUserName = trainingItemSummaryRepository.findAllByTrainerUserName(trainerUserName);
            if (allByTrainerUserName.size() == 1) {
                TrainingItemSummary trainingItemSummary = allByTrainerUserName.get(0);
                if (trainingItemSummary.getWorkload().containsKey(trainingDate.getYear())) {
                    Map<Integer, Long> yearMap = trainingItemSummary.getWorkload().get(trainingDate.getYear());
                    if (yearMap.containsKey(trainingDate.getMonthValue())) {
                        yearMap.put(trainingDate.getMonthValue(), yearMap.get(trainingDate.getMonthValue()) + trainingDuration);
                    } else {
                        yearMap.put(trainingDate.getMonthValue(), trainingDuration);
                    }
                } else {
                    trainingItemSummary.getWorkload().put(trainingDate.getYear(), Map.of(trainingDate.getMonthValue(), trainingDuration));
                }
            } else {
                trainingItemSummaryRepository.save(TrainingItemSummary.builder()
                    .trainerUserName(trainerUserName)
                    .trainerFirstName(trainerFirstName)
                    .trainerLastName(trainerLastName)
                    .workload(Map.of(trainingDate.getYear(), Map.of(trainingDate.getMonthValue(), trainingDuration)))
                    .build()
                );
            }
        } else if (actionType == ActionType.DELETE) {
            List<TrainingItemSummary> allByTrainerUserName = trainingItemSummaryRepository.findAllByTrainerUserName(trainerUserName);
            if (allByTrainerUserName.size() == 1) {
                TrainingItemSummary trainingItemSummary = allByTrainerUserName.get(0);
                if (trainingItemSummary.getWorkload().containsKey(trainingDate.getYear())) {
                    Map<Integer, Long> yearMap = trainingItemSummary.getWorkload().get(trainingDate.getYear());
                    if (yearMap.containsKey(trainingDate.getMonthValue())) {
                        yearMap.put(trainingDate.getMonthValue(), yearMap.get(trainingDate.getMonthValue()) - trainingDuration);
                    }
                }
            }
        }
    }

    @Override
    public TrainerWorkloadDTO getTrainerWorkload(String trainerUserName) {
        return trainingItemSummaryRepository.findAllByTrainerUserName(trainerUserName)
            .stream().findFirst()
            .map(trainingItemSummary -> TrainerWorkloadDTO.builder()
                .trainerUserName(trainingItemSummary.getTrainerUserName())
                .trainerFirstName(trainingItemSummary.getTrainerFirstName())
                .trainerLastName(trainingItemSummary.getTrainerLastName())
                .workload(trainingItemSummary.getWorkload())
                .build())
            .orElse(TrainerWorkloadDTO.builder().build());
    }

}
