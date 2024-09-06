package com.github.jbarus.solver;

import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import com.github.jbarus.Main;
import com.github.jbarus.pojo.UniversityEmployee;

import static ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore.ONE_HARD;

public class ComitteeSolutionConstraintProvider implements ConstraintProvider {
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                only3UniversityEmployeesPerComittee(constraintFactory),
                onlyOneHabilitatedPerComittee(constraintFactory),
                prefferLikedWorkersInComittee(constraintFactory),
                avoidDislikedWorkersInComittee(constraintFactory),
                shouldNotExceedTimeslot(constraintFactory)
        };
    }

    private Constraint only3UniversityEmployeesPerComittee(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Committee.class).filter(committee -> committee.getUniversityEmployees().size() != 3).penalize(ONE_HARD).asConstraint("Only 3 university workers per comittee");
    }

    private Constraint onlyOneHabilitatedPerComittee(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Committee.class)
                .filter(committee -> committee.getUniversityEmployees().stream().noneMatch(UniversityEmployee::isHabilitated))
                .penalize(ONE_HARD).asConstraint("Only one habilitated per comittee");
    }

    private Constraint prefferLikedWorkersInComittee(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Committee.class)
                .filter(committee -> committee.getUniversityEmployees().stream().anyMatch(
                        worker ->{
                            UniversityEmployee preferredWorker = Main.positiveCorrelation.getRelation(worker);
                            return preferredWorker != null && !committee.getUniversityEmployees().contains(preferredWorker);
                        }
                        ))
                .penalize(ONE_HARD).asConstraint("University employees who like each other should be on the same committee.");
    }

    private Constraint avoidDislikedWorkersInComittee(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Committee.class)
                .filter(committee -> committee.getUniversityEmployees().stream().anyMatch(
                        worker ->{
                            UniversityEmployee preferredWorker = Main.negativeCorrelation.getRelation(worker);
                            return preferredWorker != null && committee.getUniversityEmployees().contains(preferredWorker);
                        }
                ))
                .penalize(ONE_HARD).asConstraint("University employees who dislike each other shouldn’t be on the same committee.");
    }

    private Constraint shouldNotExceedTimeslot(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Committee.class).filter(committee -> !committee.canStudentsFitInShortestSlot()).penalize(ONE_HARD).asConstraint("Should not exceed times slot");
    }
}
