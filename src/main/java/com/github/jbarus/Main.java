package com.github.jbarus;

import ai.timefold.solver.core.api.solver.Solver;
import ai.timefold.solver.core.api.solver.SolverFactory;
import com.github.jbarus.pojo.Student;
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

        List<Student> students = List.of(
                new Student(UUID.randomUUID(), "Chrissie", "Dudek"),
                new Student(UUID.randomUUID(), "Abbott", "Higford"),
                new Student(UUID.randomUUID(), "Romy", "Chainey"),
                new Student(UUID.randomUUID(), "Tye", "Niles"),
                new Student(UUID.randomUUID(), "Charita", "Hulles"),
                new Student(UUID.randomUUID(), "Andreana", "Mayston"),
                new Student(UUID.randomUUID(), "Delora", "Hutchens"),
                new Student(UUID.randomUUID(), "Major", "Ventam"),
                new Student(UUID.randomUUID(), "Gaye", "Malenoir"),
                new Student(UUID.randomUUID(), "Lauritz", "Onians"),
                new Student(UUID.randomUUID(), "Drusie", "Sandeford"),
                new Student(UUID.randomUUID(), "Micheil", "Beakes"),
                new Student(UUID.randomUUID(), "Hilton", "Senyard"),
                new Student(UUID.randomUUID(), "Minerva", "Scoyne"),
                new Student(UUID.randomUUID(), "Jamie", "Frick"),
                new Student(UUID.randomUUID(), "Marvin", "Buxy"),
                new Student(UUID.randomUUID(), "Adriano", "Causer"),
                new Student(UUID.randomUUID(), "Natalya", "Bairstow"),
                new Student(UUID.randomUUID(), "Emalee", "Ebertz"),
                new Student(UUID.randomUUID(), "Angy", "Jeduch"),
                new Student(UUID.randomUUID(), "Emmye", "Murrells"),
                new Student(UUID.randomUUID(), "Larina", "Pratchett"),
                new Student(UUID.randomUUID(), "Selig", "Panner"),
                new Student(UUID.randomUUID(), "Crystal", "Bouch"),
                new Student(UUID.randomUUID(), "Israel", "Matthews")
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