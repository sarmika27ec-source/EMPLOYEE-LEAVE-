package com.example.employee_leave_management.controller;

import com.example.employee_leave_management.domain.LeaveBalance;
import com.example.employee_leave_management.domain.LeaveRequest;
import com.example.employee_leave_management.repository.LeaveBalanceRepository;
import com.example.employee_leave_management.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;

    @GetMapping("/pending")
    public List<LeaveRequest> pendingRequests() {
        return leaveRequestRepository.findByStatus("PENDING");
    }

    @PutMapping("/approve/{id}")
    public String approveLeave(
            @PathVariable Long id,
            @RequestParam(required = false) String comment) {

        LeaveRequest request =
                leaveRequestRepository.findById(id).orElse(null);

        if (request == null) {
            return "Leave request not found";
        }

        if (!"PENDING".equals(request.getStatus())) {
            return "Request already processed";
        }

        long days = ChronoUnit.DAYS.between(
                request.getStartDate(),
                request.getEndDate()
        ) + 1;

        LeaveBalance balance =
                leaveBalanceRepository
                        .findByEmployeeIdAndLeaveTypeId(
                                request.getEmployeeId(),
                                request.getLeaveTypeId()
                        )
                        .orElse(null);

        if (balance == null ||
                balance.getAvailableDays() < days) {

            return "Insufficient leave balance";
        }

        balance.setAvailableDays(
                balance.getAvailableDays() - (int) days
        );

        request.setStatus("APPROVED");

        request.setManagerComment(
                comment == null ? "" : comment
        );

        leaveBalanceRepository.save(balance);
        leaveRequestRepository.save(request);

        return "Leave approved";
    }

    @PutMapping("/reject/{id}")
    public String rejectLeave(
            @PathVariable Long id,
            @RequestParam(required = false) String comment) {

        LeaveRequest request =
                leaveRequestRepository.findById(id).orElse(null);

        if (request == null) {
            return "Leave request not found";
        }

        if (!"PENDING".equals(request.getStatus())) {
            return "Request already processed";
        }

        request.setStatus("REJECTED");

        request.setManagerComment(
                comment == null
                        ? "Rejected by manager"
                        : comment
        );

        leaveRequestRepository.save(request);

        return "Leave rejected";
    }
}