package com.github.jbarus.solver;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.variable.PlanningListVariable;
import com.github.jbarus.pojo.UniversityEmployee;

import java.util.*;

@PlanningEntity
public class Committee {
    @PlanningId
    private UUID id;
    @PlanningListVariable(valueRangeProviderRefs = "universityEmployeeRange", allowsUnassignedValues = true)
    List<UniversityEmployee> universityEmployees = new ArrayList<>();

    public Committee() {
        this.id = UUID.randomUUID();
    }

    public Committee(UUID id) {
        this.id = id;
    }

    public Committee(UUID id, List<UniversityEmployee> universityEmployees) {
        this.id = id;
        this.universityEmployees = universityEmployees;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<UniversityEmployee> getUniversityEmployees() {
        return universityEmployees;
    }

    public void setUniversityEmployees(List<UniversityEmployee> universityEmployees) {
        this.universityEmployees = universityEmployees;
    }

    public int getCommitteeSize(){
        return universityEmployees.stream()
                .mapToInt(employee -> employee.getReviewedStudents().size())
                .sum();
    }

    public int getCommitteeDuration(){
        return universityEmployees.stream()
                .mapToInt(UniversityEmployee::getPreferredCommitteeDuration)
                .max().orElse(20) * getCommitteeSize();
    }

    public boolean canStudentsFitInShortestSlot(){
        Optional<UniversityEmployee> employeeWithShortestSlot = universityEmployees.stream()
                .min(Comparator.comparing(UniversityEmployee::getTimeslotDuration));
        if(employeeWithShortestSlot.isPresent()){
            return employeeWithShortestSlot.get().getTimeslotDuration().toMinutes() < getCommitteeDuration();
        }else {
            return false;
        }


    }

    @Override
    public String toString() {
        return "Committee{" +
                "id=" + id +
                ", universityEmployees=" + universityEmployees +
                '}';
    }
}
