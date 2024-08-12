package com.github.jbarus.pojo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class UniversityWorker {
    private UUID id;
    private String firstName;
    private String secondName;
    private String isHabilitated;
    private List<LocalDateTime> availableTimeSlots;

    //TODO preferred colleagues
    // private List<UniversityWorker>?????

    //TODO not preferred colleagues
    // private List<UniversityWorker>


    public UniversityWorker(UUID id, String firstName, String secondName, String isHabilitated, List<LocalDateTime> availableTimeSlots) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.isHabilitated = isHabilitated;
        this.availableTimeSlots = availableTimeSlots;
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

    public String getIsHabilitated() {
        return isHabilitated;
    }

    public void setIsHabilitated(String isHabilitated) {
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

        UniversityWorker that = (UniversityWorker) o;

        if (!firstName.equals(that.firstName)) return false;
        if (!secondName.equals(that.secondName)) return false;
        return isHabilitated.equals(that.isHabilitated);
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + secondName.hashCode();
        result = 31 * result + isHabilitated.hashCode();
        return result;
    }
}
