package com.example.microservice.service;

import com.example.microservice.entity.TrainingItem;
import com.example.microservice.entity.TrainingItemSummary;
import com.example.microservice.repository.TrainingItemSummaryRepository;
import com.example.microservice.service.impl.TrainingItemSummaryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static com.example.microservice.SampleCreator.createSampleTrainingItem;
import static com.example.microservice.entity.enums.ActionType.ADD;
import static com.example.microservice.entity.enums.ActionType.DELETE;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class TrainingItemSummaryServiceTest {

    @Mock
    private TrainingItemSummaryRepository trainingItemSummaryRepository;

    @Mock
    private TrainingItemService trainingItemService;

    @InjectMocks
    private TrainingItemSummaryServiceImpl trainingItemSummaryService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateTrainingItem() {
        TrainingItem trainingItem = createSampleTrainingItem();
        trainingItemSummaryService.updateTrainingItemSummary(
            trainingItem.getTrainerUserName(),
            trainingItem.getTrainerFirstName(),
            trainingItem.getTrainerLastName(),
            trainingItem.isActive(),
            trainingItem.getTrainingDate(),
            trainingItem.getTrainingDuration(),
            ADD
        );
        verify(trainingItemSummaryRepository, times(1)).save(Mockito.any(TrainingItemSummary.class));
        verify(trainingItemSummaryRepository, times(1)).findAllByTrainerUserName(
            Mockito.anyString()
        );

        trainingItemSummaryService.updateTrainingItemSummary(
            trainingItem.getTrainerUserName(),
            trainingItem.getTrainerFirstName(),
            trainingItem.getTrainerLastName(),
            trainingItem.isActive(),
            trainingItem.getTrainingDate(),
            trainingItem.getTrainingDuration(),
            DELETE
        );
        verify(trainingItemSummaryRepository, times(2)).findAllByTrainerUserName(
            Mockito.anyString()
        );
    }


}
