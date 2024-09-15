package com.github.jbarus.solver;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.variable.PlanningListVariable;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;
import com.github.jbarus.pojo.Committee;
import com.github.jbarus.pojo.UniversityEmployee;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@PlanningEntity
@Getter
@Setter
public class CommitteeEmployeeAssignment {
    @PlanningId
    private UUID id;
    private UniversityEmployee universityEmployees;
    @PlanningVariable(valueRangeProviderRefs = "committeeRange")
    Committee committee;

    public CommitteeEmployeeAssignment() {
        this.id = UUID.randomUUID();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommitteeEmployeeAssignment that = (CommitteeEmployeeAssignment) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "CommitteeEmployeeAssignment{" +
                "id=" + id +
                ", universityEmployees=" + universityEmployees +
                ", committee=" + committee +
                '}';
    }
}
