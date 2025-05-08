package com.leavemanagement.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private Long id;
    private String name;
    private String email;
    private String role;
    private int leaveBalance;
}
