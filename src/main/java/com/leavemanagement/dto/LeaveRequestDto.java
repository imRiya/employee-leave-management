package com.leavemanagement.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRequestDto {
    private Long id;
    private Long employeeId;
    private String employeeName; // Good to have for display
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private String leaveType;
    private String status;
}
