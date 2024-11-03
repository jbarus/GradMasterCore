package com.github.jbarus.gradmastercore.solver;

import ai.timefold.solver.core.api.solver.Solver;
import ai.timefold.solver.core.api.solver.SolverFactory;
import ai.timefold.solver.core.config.score.director.ScoreDirectorFactoryConfig;
import ai.timefold.solver.core.config.solver.SolverConfig;
import ai.timefold.solver.core.config.solver.termination.TerminationConfig;
import com.github.jbarus.gradmastercore.models.Committee;
import com.github.jbarus.gradmastercore.models.Problem;
import com.github.jbarus.gradmastercore.models.Student;
import com.github.jbarus.gradmastercore.models.UniversityEmployee;

import java.util.ArrayList;
import java.util.List;

public class SolverRunner {

    public SolverRunner() {

    }

    public CommitteeSolution solve() {
        SolverConfig solverConfig = new SolverConfig()
                .withSolutionClass(CommitteeSolution.class)
                .withEntityClasses(CommitteeEmployeeAssignment.class)
                .withScoreDirectorFactory(new ScoreDirectorFactoryConfig()
                        .withConstraintProviderClass(ComitteeSolutionConstraintProvider.class))
                .withTerminationConfig(new TerminationConfig()
                        .withSecondsSpentLimit((long) Problem.getCurrentInstance().getProblemParameters().getCalculationTimeInSeconds()));

        CommitteeSolution unsolvedSolution = perpareCommitteeSolution();

        SolverFactory<CommitteeSolution> solverFactory = SolverFactory.create(solverConfig);
        Solver<CommitteeSolution> solver = solverFactory.buildSolver();

        CommitteeSolution solvedSolution = solver.solve(unsolvedSolution);

        System.out.println("Final Score: " + solvedSolution.getScore());
        printCommitteeDetails(solvedSolution);

        return solvedSolution;
    }

    private static CommitteeSolution perpareCommitteeSolution() {
        CommitteeSolution unsolvedSolution = new CommitteeSolution();

        List<UniversityEmployee> currentEmployees = Problem.getCurrentInstance().getUniversityEmployees();
        List<CommitteeEmployeeAssignment> committeeEmployeeAssignmentList = new ArrayList<>();
        for (UniversityEmployee universityEmployee : currentEmployees){
            CommitteeEmployeeAssignment committeeEmployeeAssignment = new CommitteeEmployeeAssignment();
            committeeEmployeeAssignment.setUniversityEmployees(universityEmployee);
            committeeEmployeeAssignmentList.add(committeeEmployeeAssignment);
        }

        List<Committee>committees = new ArrayList<>();
        for (int i = 0; i < currentEmployees.size() / Problem.getCurrentInstance().getProblemParameters().getCommitteeSize(); i++) {
            committees.add(new Committee("CommitteeDTO " + i));
        }

        unsolvedSolution.setCommittees(committees);
        unsolvedSolution.setCommitteeEmployeeAssignments(committeeEmployeeAssignmentList);
        unsolvedSolution.setUnassignedStudents(Problem.getCurrentInstance().getUnassignedStudents());
        return unsolvedSolution;
    }

    private void printCommitteeDetails(CommitteeSolution solution) {
        for (Committee committee : solution.getCommittees()) {
            System.out.println("CommitteeDTO: " + committee.getName());
            System.out.println("Assigned Employees:");

            for (CommitteeEmployeeAssignment assignment : solution.getCommitteeEmployeeAssignments()) {
                if (assignment.getCommittee() != null && assignment.getCommittee().equals(committee)) {
                    UniversityEmployee employee = assignment.getUniversityEmployees();
                    System.out.println("- " + employee.getFirstName() + " " + employee.getSecondName() +
                            " (Habilitated: " + employee.isHabilitated() + ")" + employee.getTimeslotStart() + " - " + employee.getTimeslotEnd() + " duration:" + employee.getPreferredCommitteeDuration());
                }
            }

            System.out.println("Assigned Students:");

            for (CommitteeEmployeeAssignment assignment : solution.getCommitteeEmployeeAssignments()) {
                if (assignment.getCommittee() != null && assignment.getCommittee().equals(committee)) {
                    List<Student> assignedStudents = assignment.getUniversityEmployees().getReviewedStudents();
                    for (Student student : assignedStudents) {
                        System.out.println("- " + student.getFirstName() + " " + student.getSecondName());
                    }
                }
            }

            System.out.println();
        }
    }

}
