package com.github.jbarus.pojo;

import java.util.UUID;

public class Student {
    private UUID id;
    private String firstName;
    private String secondName;
    private UniversityWorker supervisor;
    private UniversityWorker reviewer;

    public Student(UUID id, String firstName, String secondName, UniversityWorker supervisor, UniversityWorker reviewer) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.supervisor = supervisor;
        this.reviewer = reviewer;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public UniversityWorker getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(UniversityWorker supervisor) {
        this.supervisor = supervisor;
    }

    public UniversityWorker getReviewer() {
        return reviewer;
    }

    public void setReviewer(UniversityWorker reviewer) {
        this.reviewer = reviewer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (!firstName.equals(student.firstName)) return false;
        if (!secondName.equals(student.secondName)) return false;
        if (!supervisor.equals(student.supervisor)) return false;
        return reviewer.equals(student.reviewer);
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + secondName.hashCode();
        result = 31 * result + supervisor.hashCode();
        result = 31 * result + reviewer.hashCode();
        return result;
    }
}
