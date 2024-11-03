package com.github.jbarus.gradmastercore.models.dto;

import com.github.jbarus.gradmastercore.models.Student;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolutionDTO {
    private UUID id;
    private List<CommitteeDTO> committees;
    private List<Student> unassignedStudents;
}
