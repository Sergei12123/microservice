package com.example.microservice.repository;

import com.example.microservice.entity.TrainingItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface TrainingItemRepository extends MongoRepository<TrainingItem, String> {


    void deleteByTrainerUserNameAndTrainerFirstNameAndTrainerLastNameAndIsActiveAndTrainingDateAndTrainingDuration(
        String trainerUserName, String trainerFirstName, String trainerLastName,
        boolean isActive, LocalDate trainingDate, Long trainingDuration);

    List<TrainingItem> findAllByTrainerUserName(String trainerUserName);
}
