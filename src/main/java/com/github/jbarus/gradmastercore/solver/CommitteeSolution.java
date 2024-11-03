package com.github.jbarus.gradmastercore.solver;

import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningScore;
import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import com.github.jbarus.gradmastercore.models.Committee;
import com.github.jbarus.gradmastercore.models.Student;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@PlanningSolution
@Getter
@Setter
public class CommitteeSolution {
    @ValueRangeProvider(id = "committeeRange")
    private List<Committee> committees;
    @PlanningEntityCollectionProperty
    private List<CommitteeEmployeeAssignment> committeeEmployeeAssignments;
    @PlanningScore
    private HardSoftScore score;

    private List<Student> unassignedStudents;

    public CommitteeSolution() {
    }

    public CommitteeSolution(List<Committee> committees, List<CommitteeEmployeeAssignment> committeeEmployeeAssignments) {
        this.committees = committees;
        this.committeeEmployeeAssignments = committeeEmployeeAssignments;
    }

}
