package com.example.microservice.messaging;

import com.example.microservice.dto.TrainerWorkloadDTO;
import com.example.microservice.entity.enums.ActionType;
import com.example.microservice.messaging.dto.TrainingItemDTO;
import com.example.microservice.service.TrainingItemService;
import com.example.microservice.service.TrainingItemSummaryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import static com.example.microservice.configuration.RabbitMQConfig.QUEUE_TRAINING_ITEM_TRAINER_WORKLOAD;
import static com.example.microservice.configuration.RabbitMQConfig.QUEUE_TRAINING_ITEM_UPDATE;

@Slf4j
@Service
@AllArgsConstructor
public class TrainingItemListener {

    private final TrainingItemService trainingItemService;

    private final TrainingItemSummaryService trainingItemSummaryService;

    @RabbitListener(queues = QUEUE_TRAINING_ITEM_UPDATE)
    public void updateTrainingItem(@Payload TrainingItemDTO trainingItemDTO,
                                   @Header("actionType") ActionType actionType) {
        trainingItemService.updateTrainingItem(
            trainingItemDTO.getTrainerUserName(),
            trainingItemDTO.getTrainerFirstName(),
            trainingItemDTO.getTrainerLastName(),
            trainingItemDTO.isActive(),
            trainingItemDTO.getTrainingDate(),
            trainingItemDTO.getTrainingDuration(),
            actionType
        );
    }

    @RabbitListener(queues = QUEUE_TRAINING_ITEM_TRAINER_WORKLOAD)
    public TrainerWorkloadDTO getTrainerWorkload(@Payload String trainerUserName) {
        return trainingItemSummaryService.getTrainerWorkload(trainerUserName);
    }

}
