package com.github.jbarus;

import ai.timefold.solver.core.api.solver.Solver;
import ai.timefold.solver.core.api.solver.SolverFactory;
import com.github.jbarus.pojo.UniversityWorker;
import com.github.jbarus.solver.ComitteeSolution;
import com.github.jbarus.solver.Committee;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        SolverFactory<ComitteeSolution> solverFactory = SolverFactory.createFromXmlResource(
                "config.xml");
        Solver<ComitteeSolution> solver = solverFactory.buildSolver();

        ComitteeSolution unsolved = new ComitteeSolution();
        List<UniversityWorker> universityWorkers = List.of(
                new UniversityWorker(UUID.randomUUID(), "Alanson", "Sheran", true),
                new UniversityWorker(UUID.randomUUID(),"Leighton", "Organer", false),
                new UniversityWorker(UUID.randomUUID(),"Abby", "Krimmer", false),
                new UniversityWorker(UUID.randomUUID(),"Helge", "Tattersfield", false),
                new UniversityWorker(UUID.randomUUID(),"Heather", "Mottley", false),
                new UniversityWorker(UUID.randomUUID(), "Julianne", "Moss", true),
                new UniversityWorker(UUID.randomUUID(),"Lenee", "Josling", false),
                new UniversityWorker(UUID.randomUUID(),"Patrizio", "Kingswold", false),
                new UniversityWorker(UUID.randomUUID(),"Haily", "Witch", false),
                new UniversityWorker(UUID.randomUUID(),"Adore", "Anchor",  true)
        );

        unsolved.setUniversityWorkers(universityWorkers);

        List<Committee> committees = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            committees.add(new Committee(UUID.randomUUID()));
        }

        unsolved.setCommittees(committees);

        ComitteeSolution solved = solver.solve(unsolved);

        System.out.println("Final Score: " + solved.getScore());

        for (Committee committee : solved.getCommittees()) {
            System.out.println("Committee ID: " + committee.getId());
            System.out.println("Assigned University Workers:");
            for (UniversityWorker worker : committee.getUniversityWorkers()) {
                System.out.println("  - " + worker);
            }
            System.out.println();
        }
    }
}