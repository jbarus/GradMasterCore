package com.github.jbarus.gradmastercore.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UniversityEmployee {
    private UUID id;
    private String firstName;
    private String secondName;
    private boolean isHabilitated;
    private LocalTime timeslotStart;
    private LocalTime timeslotEnd;
    private int preferredCommitteeDuration;
    @JsonIgnore
    private List<Student> reviewedStudents = new ArrayList<>();

    public UniversityEmployee(UUID id, String firstName, String secondName, boolean isHabilitated, LocalTime timeslotStart, LocalTime timeslotEnd, int preferredCommitteeDuration, List<Student> reviewedStudents) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.isHabilitated = isHabilitated;
        this.timeslotStart = timeslotStart;
        this.timeslotEnd = timeslotEnd;
        this.preferredCommitteeDuration = preferredCommitteeDuration;
        this.reviewedStudents = reviewedStudents;
    }

    public UniversityEmployee(String firstName, String secondName, boolean isHabilitated, LocalTime timeslotStart, LocalTime timeslotEnd, int preferredCommitteeDuration, List<Student> reviewedStudents) {
        this(UUID.randomUUID(), firstName, secondName,isHabilitated,timeslotStart, timeslotEnd, preferredCommitteeDuration, reviewedStudents);
    }

    public UniversityEmployee() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UniversityEmployee that = (UniversityEmployee) o;

        if (isHabilitated != that.isHabilitated) return false;
        if (!firstName.equals(that.firstName)) return false;
        return secondName.equals(that.secondName);
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + secondName.hashCode();
        result = 31 * result + (isHabilitated ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UniversityEmployee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", isHabilitated=" + isHabilitated +
                ", timeslotStart=" + timeslotStart +
                ", timeslotEnd=" + timeslotEnd +
                ", preferredCommitteeDuration=" + preferredCommitteeDuration +
                ", reviewedStudents=" + reviewedStudents +
                '}';
    }
}
