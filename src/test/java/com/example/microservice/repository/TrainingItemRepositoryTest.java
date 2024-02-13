package com.example.microservice.repository;


import com.example.microservice.entity.TrainingItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.example.microservice.SampleCreator.createSampleTrainingItem;

@DataMongoTest
class TrainingItemRepositoryTest {

    @Autowired
    private TrainingItemRepository trainingItemRepository;

    @Test
    void deleteByTrainerUserNameAndTrainerFirstNameAndTrainerLastNameAndIsActiveAndTrainingDateAndTrainingDurationTest() {
        TrainingItem trainingItem = createSampleTrainingItem();
        trainingItemRepository.save(trainingItem);
        Assertions.assertNotNull(trainingItemRepository.findById(trainingItem.getId()).orElse(null));

        trainingItemRepository.deleteByTrainerUserNameAndTrainerFirstNameAndTrainerLastNameAndIsActiveAndTrainingDateAndTrainingDuration(
            trainingItem.getTrainerUserName(),
            trainingItem.getTrainerFirstName(),
            trainingItem.getTrainerLastName(),
            trainingItem.isActive(),
            trainingItem.getTrainingDate(),
            trainingItem.getTrainingDuration()
        );
        Assertions.assertNull(trainingItemRepository.findById(trainingItem.getId()).orElse(null));

    }


}
