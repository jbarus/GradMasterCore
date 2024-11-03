package com.github.jbarus.gradmastercore.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProblemParameters {
    private int committeeSize;
    private int maxNumberOfNonHabilitatedEmployees;
    private int calculationTimeInSeconds;

    public ProblemParameters(int committeeSize, int maxNumberOfNonHabilitatedEmployees, int calculationTimeInSeconds) {
        this.committeeSize = committeeSize;
        this.maxNumberOfNonHabilitatedEmployees = maxNumberOfNonHabilitatedEmployees;
        this.calculationTimeInSeconds = calculationTimeInSeconds;
    }

    public ProblemParameters() {
        this.committeeSize = 3;
        this.maxNumberOfNonHabilitatedEmployees = 2;
        this.calculationTimeInSeconds = 10;
    }

    @Override
    public String toString() {
        return "ProblemParameters{" +
                "committeeSize=" + committeeSize +
                ", maxNumberOfNonHabilitatedEmployees=" + maxNumberOfNonHabilitatedEmployees +
                ", calculationTimeInSeconds=" + calculationTimeInSeconds +
                '}';
    }
}
