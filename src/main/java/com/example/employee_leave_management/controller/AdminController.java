package com.example.employee_leave_management.controller;

import com.example.employee_leave_management.domain.Employee;
import com.example.employee_leave_management.domain.LeaveType;
import com.example.employee_leave_management.repository.EmployeeRepository;
import com.example.employee_leave_management.repository.LeaveTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    @GetMapping("/employees")
    public List<Employee> employees() {
        return employeeRepository.findAll();
    }

    @PostMapping("/leave-type")
    public LeaveType addLeaveType(
            @RequestBody LeaveType leaveType) {

        return leaveTypeRepository.save(leaveType);
    }

    @GetMapping("/leave-types")
    public List<LeaveType> leaveTypes() {
        return leaveTypeRepository.findAll();
    }

    @PutMapping("/leave-type/{id}")
    public LeaveType updateLeaveType(
            @PathVariable Long id,
            @RequestBody LeaveType updated) {

        LeaveType leaveType =
                leaveTypeRepository.findById(id).orElse(null);

        if (leaveType == null) {
            return null;
        }

        leaveType.setName(updated.getName());
        leaveType.setDescription(updated.getDescription());
        leaveType.setMaximumDaysPerYear(
                updated.getMaximumDaysPerYear()
        );
        leaveType.setCarryForward(
                updated.isCarryForward()
        );

        return leaveTypeRepository.save(leaveType);
    }

    @DeleteMapping("/leave-type/{id}")
    public String deleteLeaveType(
            @PathVariable Long id) {

        if (!leaveTypeRepository.existsById(id)) {
            return "Leave type not found";
        }

        leaveTypeRepository.deleteById(id);

        return "Leave type deleted";
    }
}