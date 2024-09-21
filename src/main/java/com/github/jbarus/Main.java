package com.github.jbarus;

import ai.timefold.solver.core.api.solver.Solver;
import ai.timefold.solver.core.api.solver.SolverFactory;
import com.github.jbarus.pojo.Committee;
import com.github.jbarus.pojo.Student;
import com.github.jbarus.pojo.UniversityEmployee;
import com.github.jbarus.solver.CommitteeSolution;
import com.github.jbarus.solver.CommitteeEmployeeAssignment;
import com.github.jbarus.solver.SolverContext;
import com.github.jbarus.structures.CorrelationMap;
import com.github.jbarus.utils.DataLoader;
import com.github.jbarus.utils.PrepareCommittee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {

    public static final CorrelationMap<UniversityEmployee> positiveCorrelation = new CorrelationMap<>();
    public static final CorrelationMap<UniversityEmployee> negativeCorrelation = new CorrelationMap<>();

    private static List<String> studentsColumns =new ArrayList<>(List.of("JED_STUD", "PRG_KOD", "ETP_KOD", "KIERUNEK", "RODZAJ_STUDIOW", "TRYB_STUDIOW",
            "NAZWISKO", "IMIE", "INDEKS", "EMAIL", "TELEFON", "DATA_ZLOZENIA_PRACY", "DATA_OBRONY", "CZY_CHRONIONA", "DATA_ZAMOWIENIA_DYPL",
            "TYTUL_PRACY", "PROM_IMIE", "PROM_NAZWISKO", "JEDN_ZATR_PROMOT", "RECENZENT", "JEDN_ZATR_RECEN", "SREDNIA_STUDIOW", "ECTS_UZYSKANE", "BUDYNEK",
            "SALA", "PRZEWODNICZACY", "OPIEKUN", "RECENZENT", "CZLONEK", "EGZAMINATOR", "dyplom dokument odpłatny", "suplement dokument odpłatny"));

    private static List<String> universityEmployeesColumns = new ArrayList<>(List.of("NAZWISKO", "IMIE", "CZY_HABILITOWANY", "DATA_DOSTEPNOSCI", "POCZATEK_DOSTEPNOSCI", "KONIEC_DOSTEPNOSCI", "DLUGOSC_KOMISJI"));

    public static void main(String[] args) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("KIERUNEK","Informatyka Techniczna");
        hashMap.put("DATA_OBRONY","01.02.2024");
        List<List<String>> students = DataLoader.loadData("C:\\Users\\Jakub\\Desktop\\GradMaster\\Materiały\\Dziekanat_obrony_8.02-IT.xlsx",studentsColumns,List.of("IMIE", "NAZWISKO", "RECENZENT", "DATA_OBRONY"),hashMap);

        HashMap<String, String> hashMap2 = new HashMap<>();
        hashMap2.put("DATA_DOSTEPNOSCI","2/1/24");
        List<List<String>> professors = DataLoader.loadData("C:\\Users\\Jakub\\Desktop\\GradMaster\\Materiały\\Profesorowie.xlsx",universityEmployeesColumns,List.of("NAZWISKO", "IMIE", "CZY_HABILITOWANY", "POCZATEK_DOSTEPNOSCI", "KONIEC_DOSTEPNOSCI", "DLUGOSC_KOMISJI"),hashMap2);

        CommitteeSolution unsolved = PrepareCommittee.prepareCommittee(professors, students);

        SolverContext.getInstance().setUnsolvedSolution(unsolved);

        SolverContext.getInstance().getNegativeCorrelation().addRelation(unsolved.getCommitteeEmployeeAssignments().get(0).getUniversityEmployees(),
                                        unsolved.getCommitteeEmployeeAssignments().get(1).getUniversityEmployees());
        SolverContext.getInstance().getNegativeCorrelation().addRelation(unsolved.getCommitteeEmployeeAssignments().get(6).getUniversityEmployees(),
                                        unsolved.getCommitteeEmployeeAssignments().get(8).getUniversityEmployees());
        SolverContext.getInstance().getNegativeCorrelation().addRelation(unsolved.getCommitteeEmployeeAssignments().get(13).getUniversityEmployees(),
                                        unsolved.getCommitteeEmployeeAssignments().get(15).getUniversityEmployees());

        SolverContext.getInstance().getPositiveCorrelation().addRelation(unsolved.getCommitteeEmployeeAssignments().get(5).getUniversityEmployees(),
                                        unsolved.getCommitteeEmployeeAssignments().get(7).getUniversityEmployees());
        SolverContext.getInstance().getPositiveCorrelation().addRelation(unsolved.getCommitteeEmployeeAssignments().get(12).getUniversityEmployees(),
                                        unsolved.getCommitteeEmployeeAssignments().get(13).getUniversityEmployees());
        SolverContext.getInstance().getPositiveCorrelation().addRelation(unsolved.getCommitteeEmployeeAssignments().get(14).getUniversityEmployees(),
                                        unsolved.getCommitteeEmployeeAssignments().get(15).getUniversityEmployees());

        SolverFactory<CommitteeSolution> solverFactory = SolverFactory.createFromXmlResource(
                "config.xml");
        Solver<CommitteeSolution> solver = solverFactory.buildSolver();

        CommitteeSolution solved = solver.solve(unsolved);

        System.out.println("Final Score: " + solved.getScore());

        printCommitteeDetails(solved);
    }

    public static void printCommitteeDetails(CommitteeSolution solution) {
        for (Committee committee : solution.getCommittees()) {
            System.out.println("Committee: " + committee.getName());
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

        System.out.println("Unassigned Students:");
        for (Student student : solution.getUnassignedStudents()) {
            System.out.println("- " + student.getFirstName() + " " + student.getSecondName());
        }
    }
}