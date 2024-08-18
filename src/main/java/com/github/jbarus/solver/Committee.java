package com.github.jbarus.solver;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.variable.PlanningListVariable;
import com.github.jbarus.pojo.UniversityEmployee;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@PlanningEntity
public class Committee {
    @PlanningId
    private UUID id;
    @PlanningListVariable(valueRangeProviderRefs = "universityEmployeeRange", allowsUnassignedValues = true)
    List<UniversityEmployee> universityEmployees = new ArrayList<>();

    public Committee() {
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

    @Override
    public String toString() {
        return "Committee{" +
                "id=" + id +
                ", universityEmployees=" + universityEmployees +
                '}';
    }
}
