package com.example.microservice;

import com.example.microservice.entity.TrainingItem;
import com.example.microservice.entity.TrainingItemSummary;

import java.time.LocalDate;
import java.util.Map;

public class SampleCreator {

    public static TrainingItem createSampleTrainingItem() {
        return new TrainingItem("1", "Trainer name", "Trainer first name", "Trainer last name", true, LocalDate.now(), 1L);
    }

    public static TrainingItemSummary createSampleTrainingItemSummary() {
        return new TrainingItemSummary("1", "Trainer name", "Trainer first name", "Trainer last name", true, Map.of(LocalDate.now().getYear(), Map.of(LocalDate.now().getMonthValue(), 1L)));
    }
}
