package com.github.jbarus.solver;

import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintCollectors;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import com.github.jbarus.pojo.UniversityWorker;

import static ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore.ONE_HARD;

public class ComitteeSolutionConstraintProvider implements ConstraintProvider {
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                only3UniversityWorkersPerComittee(constraintFactory),
                onlyOneHabilitatedPerComittee(constraintFactory)
        };
    }

    private Constraint only3UniversityWorkersPerComittee(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Committee.class).filter(committee -> committee.getUniversityWorkers().size() != 3).penalize(ONE_HARD).asConstraint("Only 3 university workers per comittee");
    }

    private Constraint onlyOneHabilitatedPerComittee(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Committee.class)
                .filter(committee -> committee.getUniversityWorkers().stream().noneMatch(UniversityWorker::getIsHabilitated))
                .penalize(ONE_HARD).asConstraint("Only one habilitated per comittee");
    }
}
