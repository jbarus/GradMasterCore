package com.github.jbarus.solver;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.variable.PlanningListVariable;
import com.github.jbarus.pojo.UniversityWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@PlanningEntity
public class Committee {
    @PlanningId
    private UUID id;
    @PlanningListVariable(valueRangeProviderRefs = "universityWorkerRange", allowsUnassignedValues = true)
    List<UniversityWorker> universityWorkers = new ArrayList<>();

    public Committee() {
    }

    public Committee(UUID id) {
        this.id = id;
    }

    public Committee(List<UniversityWorker> universityWorkers) {
        this.universityWorkers = universityWorkers;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<UniversityWorker> getUniversityWorkers() {
        return universityWorkers;
    }

    public void setUniversityWorkers(List<UniversityWorker> universityWorkers) {
        this.universityWorkers = universityWorkers;
    }

    @Override
    public String toString() {
        return "Committee{" +
                "id=" + id +
                ", universityWorkers=" + universityWorkers +
                '}';
    }
}
