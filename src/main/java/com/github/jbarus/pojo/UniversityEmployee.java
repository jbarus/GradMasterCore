package com.github.jbarus.pojo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class UniversityEmployee {
    private UUID id;
    private String firstName;
    private String secondName;
    private boolean isHabilitated;
    private List<LocalDateTime> availableTimeSlots;

    //TODO not preferred colleagues
    // private List<UniversityEmployee>


    public UniversityEmployee(UUID id, String firstName, String secondName, boolean isHabilitated) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.isHabilitated = isHabilitated;
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

    public boolean getIsHabilitated() {
        return isHabilitated;
    }

    public void setIsHabilitated(boolean isHabilitated) {
        this.isHabilitated = isHabilitated;
    }

    public List<LocalDateTime> getAvailableTimeSlots() {
        return availableTimeSlots;
    }

    public void setAvailableTimeSlots(List<LocalDateTime> availableTimeSlots) {
        this.availableTimeSlots = availableTimeSlots;
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
                ", isHabilitated='" + isHabilitated + '\'' +
                '}';
    }
}
