package com.github.jbarus.gradmastercore.solver;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.*;
import com.github.jbarus.gradmastercore.models.Problem;

import java.time.Duration;
import java.time.LocalTime;

import static ai.timefold.solver.core.api.score.stream.ConstraintCollectors.count;

public class ComitteeSolutionConstraintProvider implements ConstraintProvider {
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                limitProfessorsInCommittee(constraintFactory),
                atLeastOneHabilitatedProfessor(constraintFactory),
                avoidDislikedWorkersInCommittee(constraintFactory),
                preferLikedWorkersInCommittee(constraintFactory),
                validateCommitteeTimeAndStudentLoad(constraintFactory)
        };
    }

    private Constraint limitProfessorsInCommittee(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(CommitteeEmployeeAssignment.class)
                .groupBy(CommitteeEmployeeAssignment::getCommittee,
                        count())
                .filter((committee, count) -> count != Problem.getCurrentInstance().getProblemParameters().getCommitteeSize())
                .penalize(HardSoftScore.ONE_HARD, (committee, count) -> Math.abs(count - Problem.getCurrentInstance().getProblemParameters().getCommitteeSize()))
                .asConstraint("Max 3 professors per committee");
    }

    private Constraint atLeastOneHabilitatedProfessor(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(CommitteeEmployeeAssignment.class)
                .filter(assignment -> !assignment.getUniversityEmployees().isHabilitated())
                .groupBy(CommitteeEmployeeAssignment::getCommittee, count())
                .filter((assignment, count) -> count > Problem.getCurrentInstance().getProblemParameters().getMaxNumberOfNonHabilitatedEmployees())
                .penalize(HardSoftScore.ONE_HARD, (committee, count) -> count - Problem.getCurrentInstance().getProblemParameters().getMaxNumberOfNonHabilitatedEmployees())
                .asConstraint("At least one habilitated professor per committee");
    }

    private Constraint avoidDislikedWorkersInCommittee(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(CommitteeEmployeeAssignment.class)
                .join(CommitteeEmployeeAssignment.class,
                        Joiners.equal(CommitteeEmployeeAssignment::getCommittee))
                .filter((assignment1, assignment2) ->
                        Problem.getCurrentInstance().getNegativeCorrelation().containsRelation(assignment1.getUniversityEmployees(), assignment2.getUniversityEmployees()))
                .penalize( HardSoftScore.ONE_HARD).asConstraint("University employees who dislike each other shouldn’t be on the same committee.");
    }

    private Constraint preferLikedWorkersInCommittee(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(CommitteeEmployeeAssignment.class)
                .join(CommitteeEmployeeAssignment.class,
                        Joiners.equal(CommitteeEmployeeAssignment::getCommittee))
                .filter((assignment1, assignment2) ->
                        Problem.getCurrentInstance().getPositiveCorrelation().containsRelation(assignment1.getUniversityEmployees(), assignment2.getUniversityEmployees()))
                .reward( HardSoftScore.ONE_HARD).asConstraint("University employees who like each other should be on the same committee.");
    }

    private Constraint validateCommitteeTimeAndStudentLoad(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(CommitteeEmployeeAssignment.class)
                        .groupBy(
                                CommitteeEmployeeAssignment::getCommittee,
                                ConstraintCollectors.toList()
                        )
                        .penalize(
                        HardSoftScore.ONE_HARD,
                        (committee, assignments) -> {
                            long studentCount = assignments.stream()
                                    .flatMap(a -> a.getUniversityEmployees().getReviewedStudents().stream())
                                    .distinct()
                                    .count();

                            int maxPreferredDuration = assignments.stream()
                                    .mapToInt(a -> a.getUniversityEmployees().getPreferredCommitteeDuration())
                                    .max()
                                    .orElse(0);

                            LocalTime latestStart = assignments.stream()
                                    .map(a -> a.getUniversityEmployees().getTimeslotStart())
                                    .max(LocalTime::compareTo)
                                    .orElse(LocalTime.MIN);

                            LocalTime earliestEnd = assignments.stream()
                                    .map(a -> a.getUniversityEmployees().getTimeslotEnd())
                                    .min(LocalTime::compareTo)
                                    .orElse(LocalTime.MAX);

                            long availableMinutes = Duration.between(latestStart, earliestEnd).toMinutes();

                            long excessMinutes = studentCount * maxPreferredDuration - availableMinutes;
                            return (int) Math.max(excessMinutes, 0);
                        }).asConstraint("Penalty for exceeding time constraints");
    }
}
