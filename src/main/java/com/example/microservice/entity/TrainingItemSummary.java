package com.example.microservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Document("training_summary")
@CompoundIndexes({
    @CompoundIndex(name = "trainer_first_last_name_index", def = "{'trainerFirstName': 1, 'trainerLastName': 1}")
})
public class TrainingItemSummary {

    @Id
    private String id;
    private String trainerUserName;
    private String trainerFirstName;
    private String trainerLastName;
    private boolean isActive;
    private Map<Integer, Map<Integer, Long>> workload;

}
