package com.example.employee_leave_management.repository;

import com.example.employee_leave_management.domain.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestRepository
        extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByEmployeeId(Long employeeId);

    List<LeaveRequest> findByStatus(String status);

    @Query("""
        SELECT l FROM LeaveRequest l
        WHERE l.employeeId = :employeeId
        AND l.status IN ('PENDING', 'APPROVED')
        AND l.startDate <= :endDate
        AND l.endDate >= :startDate
    """)
    List<LeaveRequest> findOverlappingRequests(
            @Param("employeeId") Long employeeId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}