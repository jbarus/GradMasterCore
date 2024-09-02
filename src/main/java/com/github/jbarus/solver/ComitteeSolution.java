package com.github.jbarus.solver;

import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningScore;
import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import com.github.jbarus.pojo.Student;
import com.github.jbarus.pojo.UniversityEmployee;

import java.util.List;

@PlanningSolution
public class ComitteeSolution {
    @ValueRangeProvider(id = "universityEmployeeRange")
    private List<UniversityEmployee> universityEmployees;
    @PlanningEntityCollectionProperty
    private List<Committee> committees;
    @PlanningScore
    private HardSoftScore score;

    private List<Student> unassignedStudents;

    public ComitteeSolution() {
    }

    public ComitteeSolution(List<UniversityEmployee> universityEmployees, List<Committee> committees) {
        this.universityEmployees = universityEmployees;
        this.committees = committees;
    }

    public List<UniversityEmployee> getUniversityEmployees() {
        return universityEmployees;
    }

    public void setUniversityEmployees(List<UniversityEmployee> universityEmployees) {
        this.universityEmployees = universityEmployees;
    }

    public List<Committee> getCommittees() {
        return committees;
    }

    public void setCommittees(List<Committee> committees) {
        this.committees = committees;
    }

    public HardSoftScore getScore() {
        return score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }

    public List<Student> getUnassignedStudents() {
        return unassignedStudents;
    }

    public void setUnassignedStudents(List<Student> unassignedStudents) {
        this.unassignedStudents = unassignedStudents;
    }
}
