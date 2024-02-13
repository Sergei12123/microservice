package com.example.microservice.service;

import com.example.microservice.entity.TrainingItem;
import com.example.microservice.repository.TrainingItemRepository;
import com.example.microservice.service.impl.TrainingItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static com.example.microservice.SampleCreator.createSampleTrainingItem;
import static com.example.microservice.entity.enums.ActionType.ADD;
import static com.example.microservice.entity.enums.ActionType.DELETE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TrainingItemServiceTest {

    @Mock
    private TrainingItemRepository trainingItemRepository;

    @Mock
    private TrainingItemSummaryService trainingItemSummaryService;

    @InjectMocks
    private TrainingItemServiceImpl trainingItemService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateTrainingItem() {
        TrainingItem trainingItem = createSampleTrainingItem();
        trainingItemService.updateTrainingItem(
            trainingItem.getTrainerUserName(),
            trainingItem.getTrainerFirstName(),
            trainingItem.getTrainerLastName(),
            trainingItem.isActive(),
            trainingItem.getTrainingDate(),
            trainingItem.getTrainingDuration(),
            ADD
        );
        verify(trainingItemRepository, times(1)).save(Mockito.any(TrainingItem.class));

        trainingItemService.updateTrainingItem(
            trainingItem.getTrainerUserName(),
            trainingItem.getTrainerFirstName(),
            trainingItem.getTrainerLastName(),
            trainingItem.isActive(),
            trainingItem.getTrainingDate(),
            trainingItem.getTrainingDuration(),
            DELETE
        );
        verify(trainingItemRepository, times(1)).deleteByTrainerUserNameAndTrainerFirstNameAndTrainerLastNameAndIsActiveAndTrainingDateAndTrainingDuration(
            Mockito.anyString(),
            Mockito.anyString(),
            Mockito.anyString(),
            Mockito.anyBoolean(),
            Mockito.any(LocalDate.class),
            Mockito.anyLong()
        );

    }


}
