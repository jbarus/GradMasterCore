package com.github.jbarus.utils;

import com.github.jbarus.pojo.Committee;
import com.github.jbarus.pojo.Student;
import com.github.jbarus.pojo.UniversityEmployee;
import com.github.jbarus.solver.ComitteeSolution;
import com.github.jbarus.solver.CommitteeEmployeeAssignment;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PrepareCommittee {
    public static ComitteeSolution prepareCommittee(List<List<String>> universityEmployees, List<List<String>> students) {
        List<UniversityEmployee> universityEmployeesObjects = universityEmployees.stream().map(entry ->
                new UniversityEmployee(
                        entry.get(1),
                        entry.get(0),
                        convertStringToBoolean(entry.get(2)),
                        LocalTime.parse(entry.get(3), DateTimeFormatter.ofPattern("h:mm:ss a")),
                        LocalTime.parse(entry.get(4), DateTimeFormatter.ofPattern("h:mm:ss a")),
                        Integer.parseInt(entry.get(5)))).collect(Collectors.toCollection(ArrayList::new));

        HashMap<String, List<Student>> professorToStudentsMap = students.stream()
                .collect(Collectors.groupingBy(
                        entry -> entry.get(3),
                        HashMap::new,
                        Collectors.mapping(
                                entry -> new Student(entry.get(1), entry.get(0)),
                                Collectors.toCollection(ArrayList::new)
                        )
                ));

        for (UniversityEmployee universityEmployee : universityEmployeesObjects) {
            if(professorToStudentsMap.containsKey(universityEmployee.getSecondName()+" "+universityEmployee.getFirstName())){
                universityEmployee.getReviewedStudents().addAll(professorToStudentsMap.remove(universityEmployee.getSecondName()+" "+universityEmployee.getFirstName()));
            }
        }
        List<Student> unallocatedStudents = new ArrayList<>();
        for (Map.Entry<String, List<Student>> entry : professorToStudentsMap.entrySet()) {
            unallocatedStudents.addAll(entry.getValue());
        }
        ComitteeSolution comitteeSolution = new ComitteeSolution();
        List<CommitteeEmployeeAssignment> committeeEmployeeAssignmentList = new ArrayList<>();
        for (UniversityEmployee universityEmployeesObject : universityEmployeesObjects) {
            CommitteeEmployeeAssignment committeeEmployeeAssignment = new CommitteeEmployeeAssignment();
            committeeEmployeeAssignment.setUniversityEmployees(universityEmployeesObject);
            committeeEmployeeAssignmentList.add(committeeEmployeeAssignment);
        }
        List<Committee>committees = new ArrayList<>();
        for (int i = 0; i < universityEmployeesObjects.size()/3; i++) {
            committees.add(new Committee("Committee " + i));
        }
        comitteeSolution.setCommittees(committees);
        comitteeSolution.setCommitteeEmployeeAssignments(committeeEmployeeAssignmentList);
        comitteeSolution.setUnassignedStudents(unallocatedStudents);
        return comitteeSolution;
    }

    private static boolean convertStringToBoolean(String booleanValue) {
        return booleanValue.equalsIgnoreCase("Tak");
    }
}
