package com.example.employee_leave_management.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "leave_type")
public class LeaveType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Integer maximumDaysPerYear;

    private boolean carryForward;

    public LeaveType() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getMaximumDaysPerYear() {
        return maximumDaysPerYear;
    }

    public boolean isCarryForward() {
        return carryForward;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMaximumDaysPerYear(Integer maximumDaysPerYear) {
        this.maximumDaysPerYear = maximumDaysPerYear;
    }

    public void setCarryForward(boolean carryForward) {
        this.carryForward = carryForward;
    }
}