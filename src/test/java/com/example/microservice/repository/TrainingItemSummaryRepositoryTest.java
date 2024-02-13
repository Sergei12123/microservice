package com.example.microservice.repository;


import com.example.microservice.entity.TrainingItemSummary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;
import java.util.Optional;

import static com.example.microservice.SampleCreator.createSampleTrainingItemSummary;

@DataMongoTest
class TrainingItemSummaryRepositoryTest {

    @Autowired
    private TrainingItemSummaryRepository trainingItemSummaryRepository;

    @Test
    void findByTrainerUserNameTest() {
        TrainingItemSummary trainingItemSummary = createSampleTrainingItemSummary();
        trainingItemSummaryRepository.save(trainingItemSummary);
        List<TrainingItemSummary> foundTrainingItemSummary = trainingItemSummaryRepository.findAllByTrainerUserName(trainingItemSummary.getTrainerUserName());
        Assertions.assertEquals(1, foundTrainingItemSummary.size());
        Assertions.assertEquals(
            foundTrainingItemSummary.get(0).getId(),
            trainingItemSummary.getId()
        );

    }


}
