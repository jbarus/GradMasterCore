package com.github.jbarus.solver;

import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningScore;
import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import com.github.jbarus.pojo.UniversityWorker;

import java.util.List;

@PlanningSolution
public class ComitteeSolution {
    @ValueRangeProvider(id = "universityWorkerRange")
    private List<UniversityWorker> universityWorkers;
    @PlanningEntityCollectionProperty
    private List<Committee> committees;
    @PlanningScore
    private HardSoftScore score;

    public ComitteeSolution() {
    }

    public ComitteeSolution(List<UniversityWorker> universityWorkers, List<Committee> committees) {
        this.universityWorkers = universityWorkers;
        this.committees = committees;
    }

    public List<UniversityWorker> getUniversityWorkers() {
        return universityWorkers;
    }

    public void setUniversityWorkers(List<UniversityWorker> universityWorkers) {
        this.universityWorkers = universityWorkers;
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
}
