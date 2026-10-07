package com.example.employee_leave_management.controller;

import com.example.employee_leave_management.domain.LeaveBalance;
import com.example.employee_leave_management.domain.LeaveRequest;
import com.example.employee_leave_management.domain.LeaveType;
import com.example.employee_leave_management.repository.LeaveBalanceRepository;
import com.example.employee_leave_management.repository.LeaveRequestRepository;
import com.example.employee_leave_management.repository.LeaveTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/api/leave")
public class LeaveController {

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @GetMapping("/types")
    public List<LeaveType> getLeaveTypes() {
        return leaveTypeRepository.findAll();
    }

    @GetMapping("/balance/{employeeId}")
    public List<LeaveBalance> getBalance(
            @PathVariable Long employeeId) {

        return leaveBalanceRepository.findByEmployeeId(employeeId);
    }

    @GetMapping("/requests/{employeeId}")
    public List<LeaveRequest> getRequests(
            @PathVariable Long employeeId) {

        return leaveRequestRepository.findByEmployeeId(employeeId);
    }

    @PostMapping("/apply")
    public String applyLeave(
            @RequestBody LeaveRequest request) {

        if (request.getStartDate() == null ||
                request.getEndDate() == null) {

            return "Start date and end date are required";
        }

        if (request.getEndDate()
                .isBefore(request.getStartDate())) {

            return "End date cannot be before start date";
        }

        long days = ChronoUnit.DAYS.between(
                request.getStartDate(),
                request.getEndDate()
        ) + 1;

        List<LeaveRequest> overlapping =
                leaveRequestRepository.findOverlappingRequests(
                        request.getEmployeeId(),
                        request.getStartDate(),
                        request.getEndDate()
                );

        if (!overlapping.isEmpty()) {
            return "Leave dates overlap with an existing request";
        }

        LeaveBalance balance =
                leaveBalanceRepository
                        .findByEmployeeIdAndLeaveTypeId(
                                request.getEmployeeId(),
                                request.getLeaveTypeId()
                        )
                        .orElse(null);

        if (balance == null) {
            return "Leave balance not found";
        }

        if (balance.getAvailableDays() < days) {
            return "Insufficient leave balance";
        }

        request.setStatus("PENDING");
        request.setManagerComment("");

        leaveRequestRepository.save(request);

        return "Leave applied successfully";
    }
}