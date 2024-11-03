package com.github.jbarus.gradmastercore.models;

import com.github.jbarus.gradmastercore.structures.CorrelationMap;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Problem {
    @Getter
    @Setter
    private static Problem currentInstance = null;

    private UUID id;
    private ProblemParameters problemParameters;
    private List<UniversityEmployee> universityEmployees;
    private CorrelationMap<UniversityEmployee> positiveCorrelation = new CorrelationMap<>();
    private CorrelationMap<UniversityEmployee> negativeCorrelation = new CorrelationMap<>();
    private List<Student> unassignedStudents;

    public Problem(UUID id, ProblemParameters problemParameters, List<UniversityEmployee> universityEmployees, CorrelationMap<UniversityEmployee> positiveCorrelation, CorrelationMap<UniversityEmployee> negativeCorrelation, List<Student> unassignedStudents) {
        this.id = id;
        this.problemParameters = problemParameters;
        this.universityEmployees = universityEmployees;
        this.positiveCorrelation = positiveCorrelation;
        this.negativeCorrelation = negativeCorrelation;
        this.unassignedStudents = unassignedStudents;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "id=" + id +
                ", problemParameters=" + problemParameters +
                ", universityEmployees=" + universityEmployees +
                ", positiveCorrelation=" + positiveCorrelation +
                ", negativeCorrelation=" + negativeCorrelation +
                ", unassignedStudents=" + unassignedStudents +
                '}';
    }
}
