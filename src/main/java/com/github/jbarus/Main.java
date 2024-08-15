package com.github.jbarus;

import ai.timefold.solver.core.api.solver.Solver;
import ai.timefold.solver.core.api.solver.SolverFactory;
import com.github.jbarus.pojo.UniversityEmployee;
import com.github.jbarus.solver.ComitteeSolution;
import com.github.jbarus.solver.Committee;
import com.github.jbarus.structures.CorrelationMap;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {

    public static final CorrelationMap<UniversityEmployee> positiveCorrelation = new CorrelationMap<>();
    public static final CorrelationMap<UniversityEmployee> negativeCorrelation = new CorrelationMap<>();

    public static void main(String[] args) {
        SolverFactory<ComitteeSolution> solverFactory = SolverFactory.createFromXmlResource(
                "config.xml");
        Solver<ComitteeSolution> solver = solverFactory.buildSolver();

        ComitteeSolution unsolved = new ComitteeSolution();
        List<UniversityEmployee> universityEmployees = List.of(
                new UniversityEmployee(UUID.randomUUID(), "Alanson", "Sheran", true),
                new UniversityEmployee(UUID.randomUUID(),"Leighton", "Organer", false),
                new UniversityEmployee(UUID.randomUUID(),"Abby", "Krimmer", false),
                new UniversityEmployee(UUID.randomUUID(),"Helge", "Tattersfield", false),
                new UniversityEmployee(UUID.randomUUID(),"Heather", "Mottley", false),
                new UniversityEmployee(UUID.randomUUID(), "Julianne", "Moss", true),
                new UniversityEmployee(UUID.randomUUID(),"Lenee", "Josling", false),
                new UniversityEmployee(UUID.randomUUID(),"Patrizio", "Kingswold", false),
                new UniversityEmployee(UUID.randomUUID(),"Haily", "Witch", false),
                new UniversityEmployee(UUID.randomUUID(),"Adore", "Anchor",  true)
        );
        positiveCorrelation.addRelation(universityEmployees.get(0), universityEmployees.get(4)); // Alanson and Heather
        positiveCorrelation.addRelation(universityEmployees.get(1), universityEmployees.get(5)); // Leighton and Julianne
        positiveCorrelation.addRelation(universityEmployees.get(2), universityEmployees.get(9)); // Abby and Adore

        negativeCorrelation.addRelation(universityEmployees.get(0), universityEmployees.get(1)); // Alanson and Leighton
        negativeCorrelation.addRelation(universityEmployees.get(3), universityEmployees.get(4)); // Helge and Abby
        negativeCorrelation.addRelation(universityEmployees.get(4), universityEmployees.get(5)); //Heather and Julianne

        unsolved.setUniversityEmployees(universityEmployees);

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
            for (UniversityEmployee worker : committee.getUniversityEmployees()) {
                System.out.println("  - " + worker);
            }
            System.out.println();
        }
    }
}