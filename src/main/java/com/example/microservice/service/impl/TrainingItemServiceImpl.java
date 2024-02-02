package com.example.microservice.service.impl;

import com.example.microservice.dto.TrainerWorkloadDTO;
import com.example.microservice.entity.TrainingItem;
import com.example.microservice.entity.enums.ActionType;
import com.example.microservice.repository.TrainingItemRepository;
import com.example.microservice.service.TrainingItemService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainingItemServiceImpl  implements TrainingItemService {

    private final TrainingItemRepository trainingItemRepository;


    public TrainingItemServiceImpl(TrainingItemRepository trainingItemRepository) {
        this.trainingItemRepository = trainingItemRepository;
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
    }

    @Override
    public TrainerWorkloadDTO getTrainerWorkload(String trainerUserName) {
        List<TrainingItem> allByTrainerUserName = trainingItemRepository.findAllByTrainerUserName(trainerUserName);
        if (!allByTrainerUserName.isEmpty()) {
            return TrainerWorkloadDTO.builder()
                .trainerUserName(allByTrainerUserName.get(0).getTrainerUserName())
                .trainerFirstName(allByTrainerUserName.get(0).getTrainerFirstName())
                .trainerLastName(allByTrainerUserName.get(0).getTrainerLastName())
                .status(allByTrainerUserName.get(0).isActive())
                .workload(allByTrainerUserName.stream().collect(Collectors.groupingBy(
                    item -> item.getTrainingDate().getYear(),
                    Collectors.groupingBy(
                        el -> el.getTrainingDate().getMonthValue(),
                        Collectors.summingLong(TrainingItem::getTrainingDuration))
                )))
                .build();
        }
        return TrainerWorkloadDTO.builder().build();
    }

}
