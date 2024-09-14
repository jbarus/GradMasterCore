package com.github.jbarus.solver;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.variable.PlanningListVariable;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;
import com.github.jbarus.pojo.UniversityEmployee;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@PlanningEntity
@Getter
@Setter
public class Committee {
    @PlanningId
    private UUID id;
    @PlanningVariable(valueRangeProviderRefs = "universityEmployeeRange")
    UniversityEmployee firstUniversityEmployee;
    @PlanningVariable(valueRangeProviderRefs = "universityEmployeeRange")
    UniversityEmployee secondUniversityEmployee;
    @PlanningVariable(valueRangeProviderRefs = "universityEmployeeRange")
    UniversityEmployee thirdUniversityEmployee;

    public Committee() {
        this.id = UUID.randomUUID();
    }

    public List<UniversityEmployee> getUniversityEmployees() {
        return List.of(firstUniversityEmployee, secondUniversityEmployee, thirdUniversityEmployee);
    }

    public int getCommitteeSize(){
        return firstUniversityEmployee.getReviewedStudents().size() +
                secondUniversityEmployee.getReviewedStudents().size() +
                thirdUniversityEmployee.getReviewedStudents().size();
    }

    public int getCommitteeDuration(){
        return Math.max(Math.max(firstUniversityEmployee.getPreferredCommitteeDuration(),secondUniversityEmployee.getPreferredCommitteeDuration()),thirdUniversityEmployee.getPreferredCommitteeDuration()) * getCommitteeSize();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Committee committee = (Committee) o;
        return Objects.equals(firstUniversityEmployee, committee.firstUniversityEmployee) && Objects.equals(secondUniversityEmployee, committee.secondUniversityEmployee) && Objects.equals(thirdUniversityEmployee, committee.thirdUniversityEmployee);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(firstUniversityEmployee);
        result = 31 * result + Objects.hashCode(secondUniversityEmployee);
        result = 31 * result + Objects.hashCode(thirdUniversityEmployee);
        return result;
    }

    @Override
    public String toString() {
        return "Committee{" +
                "id=" + id +
                ", firstUniversityEmployee=" + firstUniversityEmployee +
                ", secondUniversityEmployee=" + secondUniversityEmployee +
                ", thirdUniversityEmployee=" + thirdUniversityEmployee +
                '}';
    }
}
