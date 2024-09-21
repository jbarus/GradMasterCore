package com.github.jbarus.solver;

import com.github.jbarus.pojo.Committee;
import com.github.jbarus.pojo.Student;
import com.github.jbarus.pojo.UniversityEmployee;
import com.github.jbarus.structures.CorrelationMap;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class SolverContext {
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private static SolverContext INSTANCE;

    private UUID id;
    private List<UniversityEmployee> universityEmployeeList = new ArrayList<>();
    private List<Student> studentList = new ArrayList<>();
    private List<Committee> committeeList = new ArrayList<>();
    private int comitteeSize = 3;
    private int maxNumberOfNonHabilitatedEmployees = comitteeSize - 1;
    private CorrelationMap<UniversityEmployee> positiveCorrelation = new CorrelationMap<>();
    private CorrelationMap<UniversityEmployee> negativeCorrelation = new CorrelationMap<>();
    private CommitteeSolution unsolvedSolution;
    private CommitteeSolution solvedSolution;

    private SolverContext(){};

    public static SolverContext getInstance(){
        if(INSTANCE == null){
            INSTANCE = new SolverContext();
        }
        return INSTANCE;
    }
}
