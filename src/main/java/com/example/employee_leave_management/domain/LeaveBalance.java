package com.example.employee_leave_management.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "leave_balance")
public class LeaveBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeId;

    private Long leaveTypeId;

    private Integer availableDays;

    public LeaveBalance() {
    }

    public Long getId() {
        return id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public Long getLeaveTypeId() {
        return leaveTypeId;
    }

    public Integer getAvailableDays() {
        return availableDays;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public void setLeaveTypeId(Long leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    public void setAvailableDays(Integer availableDays) {
        this.availableDays = availableDays;
    }
}