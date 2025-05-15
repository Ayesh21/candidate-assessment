package com.teleport.candidate_assessment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/** The type Reference. */
@Document(collection = "reference")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reference {
    @Id
    private String id;

    private String userId;
    private String projectId;
    private String taskId;

}
