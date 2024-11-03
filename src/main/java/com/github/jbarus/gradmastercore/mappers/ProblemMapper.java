package com.github.jbarus.gradmastercore.mappers;

import com.github.jbarus.gradmastercore.models.Problem;
import com.github.jbarus.gradmastercore.models.Student;
import com.github.jbarus.gradmastercore.models.UniversityEmployee;
import com.github.jbarus.gradmastercore.models.dto.ProblemDTO;
import com.github.jbarus.gradmastercore.structures.CorrelationMap;

import java.util.*;
import java.util.stream.Collectors;

public class ProblemMapper {
    public static Problem problemDTOToProblemConverter(ProblemDTO problemDTO) {
        return new Problem(
                problemDTO.getId(),
                problemDTO.getProblemParameters(),
                populateReviewedStudents(problemDTO),
                populateCorrelationMap(problemDTO.getPositiveCorrelationMapping(),problemDTO),
                populateCorrelationMap(problemDTO.getNegativeCorrelationMapping(),problemDTO),
                populateUnassignedStudents(problemDTO)
        );
    }

    private static List<Student> populateUnassignedStudents(ProblemDTO problemDTO) {
        Set<UUID> assignedStudentIds = problemDTO.getStudentReviewerMapping()
                .values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toSet());

        List<Student> unassignedStudents = problemDTO.getStudents()
                .stream()
                .filter(student -> !assignedStudentIds.contains(student.getId()))
                .collect(Collectors.toList());

        System.out.println(unassignedStudents);

        return unassignedStudents;
    }

    private static CorrelationMap<UniversityEmployee> populateCorrelationMap(List<UUID> correlationList, ProblemDTO problemDTO) {
        CorrelationMap<UniversityEmployee> correlationMap = new CorrelationMap<>();
        HashMap<UUID, UniversityEmployee> universityEmployeeHashMap = new HashMap<>();
        for (UniversityEmployee universityEmployee : problemDTO.getUniversityEmployees()) {
            universityEmployeeHashMap.put(universityEmployee.getId(), universityEmployee);
        }
        for (int i = 0; i <= correlationList.size() - 1; i += 2) {
            correlationMap.addRelation(universityEmployeeHashMap.get(correlationList.get(i)), universityEmployeeHashMap.get(correlationList.get(i + 1)));
        }
        return correlationMap;
    }

    private static List<UniversityEmployee> populateReviewedStudents(ProblemDTO problemDTO) {
        for (UniversityEmployee universityEmployee : problemDTO.getUniversityEmployees()){
            List<UUID> reviewedStudents = problemDTO.getStudentReviewerMapping().get(universityEmployee.getId());
            if(reviewedStudents == null){
                continue;
            }
            for (Student student : problemDTO.getStudents()){
                if(reviewedStudents.contains(student.getId())){
                    universityEmployee.getReviewedStudents().add(student);
                }
            }
        }
        return problemDTO.getUniversityEmployees();
    }
}
