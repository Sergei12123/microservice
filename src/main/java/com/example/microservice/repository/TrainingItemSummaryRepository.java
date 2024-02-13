package com.example.microservice.repository;

import com.example.microservice.entity.TrainingItemSummary;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TrainingItemSummaryRepository extends MongoRepository<TrainingItemSummary, String> {

    List<TrainingItemSummary> findAllByTrainerUserName(String trainerUserName);
}
