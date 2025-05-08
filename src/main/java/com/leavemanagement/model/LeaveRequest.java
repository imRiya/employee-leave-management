package com.leavemanagement.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "leave_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private String leaveType; // e.g., "SICK", "VACATION", "PERSONAL"

    @Column(nullable = false)
    private String status; // e.g., "PENDING", "APPROVED", "REJECTED"
}