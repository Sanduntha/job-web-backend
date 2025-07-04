package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployerDto {
    private Long id;
    private String name;
    private String address;
    private String contactNumber;
    private String companyName;
    private Long userId;
}
