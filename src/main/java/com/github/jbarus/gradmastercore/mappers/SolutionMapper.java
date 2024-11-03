package com.github.jbarus.gradmastercore.mappers;

import com.github.jbarus.gradmastercore.models.Committee;
import com.github.jbarus.gradmastercore.models.Problem;
import com.github.jbarus.gradmastercore.models.UniversityEmployee;
import com.github.jbarus.gradmastercore.models.dto.CommitteeDTO;
import com.github.jbarus.gradmastercore.models.dto.SolutionDTO;
import com.github.jbarus.gradmastercore.solver.CommitteeEmployeeAssignment;
import com.github.jbarus.gradmastercore.solver.CommitteeSolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SolutionMapper {
    public static SolutionDTO convertCommitteeSolutionToSolutionDTO(CommitteeSolution solution) {
        System.out.println(solution.getUnassignedStudents());
        return SolutionDTO
                .builder()
                .id(Problem.getCurrentInstance().getId())
                .committees(prepareCommittees(solution))
                .unassignedStudents(solution.getUnassignedStudents())
                .build();
    }

    public static List<CommitteeDTO> prepareCommittees(CommitteeSolution solution) {
        HashMap<Committee, List<UniversityEmployee>> employeeByCommitteeMapping = new HashMap<>();

        for (CommitteeEmployeeAssignment assignment : solution.getCommitteeEmployeeAssignments()) {
            Committee committee = assignment.getCommittee();
            UniversityEmployee employee = assignment.getUniversityEmployees();

            employeeByCommitteeMapping
                    .computeIfAbsent(committee, k -> new ArrayList<>())
                    .add(employee);
        }

        List<CommitteeDTO> committees = new ArrayList<>();
        for (Map.Entry<Committee, List<UniversityEmployee>> entry : employeeByCommitteeMapping.entrySet()) {
            CommitteeDTO committeeDTO = new CommitteeDTO();
            committeeDTO.setUniversityEmployees(entry.getValue());
            committeeDTO.setStudents(entry.getValue().stream()
                    .flatMap(employee -> employee.getReviewedStudents().stream())
                    .collect(Collectors.toList()));
            committees.add(committeeDTO);
        }

        return committees;
    }
}
