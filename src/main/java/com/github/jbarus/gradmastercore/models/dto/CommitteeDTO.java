package com.github.jbarus.gradmastercore.models.dto;

import com.github.jbarus.gradmastercore.models.Student;
import com.github.jbarus.gradmastercore.models.UniversityEmployee;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CommitteeDTO {
    List<UniversityEmployee> universityEmployees;
    List<Student> students;


    public CommitteeDTO(List<UniversityEmployee> universityEmployees, List<Student> students) {
        this.universityEmployees = universityEmployees;
        this.students = students;
    }

    public CommitteeDTO() {
        students = new ArrayList<>();
        universityEmployees = new ArrayList<>();
    }
}
