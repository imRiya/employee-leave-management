package com.leavemanagement.dto;


import lombok.Data;
import java.time.LocalDate;

@Data
public class LeaveRequestCreateDto {
    private Long employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private String leaveType;
}
