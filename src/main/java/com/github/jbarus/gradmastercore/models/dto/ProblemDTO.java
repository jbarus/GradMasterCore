package com.github.jbarus.gradmastercore.models.dto;

import com.github.jbarus.gradmastercore.models.ProblemParameters;
import com.github.jbarus.gradmastercore.models.Student;
import com.github.jbarus.gradmastercore.models.UniversityEmployee;
import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemDTO {
    private UUID id;
    private List<UniversityEmployee> universityEmployees;
    private List<Student> students;
    private HashMap<UUID, List<UUID>> studentReviewerMapping;
    private List<UUID> positiveCorrelationMapping;
    private List<UUID> negativeCorrelationMapping;
    private List<UUID> splittedUniversityEmployees;
    private ProblemParameters problemParameters;

    @Override
    public String toString() {
        return "ProblemDTO{" + "\n" +
                "id=" + id + "\n" +
                ", universityEmployees=" + universityEmployees + "\n" +
                ", students=" + students + "\n" +
                ", studentReviewerMapping=" + studentReviewerMapping + "\n" +
                ", positiveCorrelationMapping=" + positiveCorrelationMapping + "\n" +
                ", negativeCorrelationMapping=" + negativeCorrelationMapping + "\n" +
                ", splittedUniversityEmployees=" + splittedUniversityEmployees + "\n" +
                ", problemParameters=" + problemParameters + "\n" +
                '}';
    }
}
