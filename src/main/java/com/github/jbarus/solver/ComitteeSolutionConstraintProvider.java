package com.github.jbarus.solver;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.*;
import com.github.jbarus.Main;
import com.github.jbarus.pojo.UniversityEmployee;

import java.util.List;

import static ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore.ONE_HARD;
import static ai.timefold.solver.core.api.score.stream.Joiners.equal;

public class ComitteeSolutionConstraintProvider implements ConstraintProvider {
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                uniqueCommitteeProfessorAssignment(constraintFactory),
                uniqueGlobalProfessorAssignment(constraintFactory),
                atLeastOneHabilitatedUniversityEmployee(constraintFactory),
                doNotExceedDeclaredTime(constraintFactory),
        };
    }

    private Constraint uniqueCommitteeProfessorAssignment(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Committee.class)
                .filter(committee -> committee.getFirstUniversityEmployee().equals(committee.getSecondUniversityEmployee()) ||
                        committee.getFirstUniversityEmployee().equals(committee.getThirdUniversityEmployee()) ||
                        committee.getSecondUniversityEmployee().equals(committee.getThirdUniversityEmployee()))
                .penalize(HardSoftScore.ONE_HARD).asConstraint("Committee must have 3 different professors");
    }

    private Constraint uniqueGlobalProfessorAssignment(ConstraintFactory constraintFactory) {
        return constraintFactory.forEachUniquePair(Committee.class)
                .filter((committee1, committee2) ->
                        committee1.getUniversityEmployees().stream().anyMatch(professor ->
                                committee2.getUniversityEmployees().contains(professor)))
                .penalize(HardSoftScore.ONE_HARD).asConstraint("Professor must not be assigned to more than one committee per session");
    }

    private Constraint atLeastOneHabilitatedUniversityEmployee(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Committee.class)
                .filter(committee -> committee.getUniversityEmployees().stream().noneMatch(UniversityEmployee::isHabilitated))
                .penalize(HardSoftScore.ONE_HARD).asConstraint("Committee must have at least one habilitated university employee");
    }

    private Constraint doNotExceedDeclaredTime(ConstraintFactory constraintFactory){
        return constraintFactory.forEach(Committee.class).filter(committee -> {
            int requiredTime = committee.getCommitteeDuration();
            int minAvailableTime = committee.getUniversityEmployees().stream().mapToInt(employee->(employee.getTimeslotEnd().toSecondOfDay() - employee.getTimeslotStart().toSecondOfDay())/60).min().orElse(0);
            return requiredTime > minAvailableTime;
        }).penalize(ONE_HARD).asConstraint("Committee exceeds available time slot");
    }

    /*private Constraint only3UniversityEmployeesPerComittee(ConstraintFactory constraintFactory) {
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
    }*/
}
