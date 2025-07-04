package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentDto {
    private Long courseId;
    private Long jobSeekerId;
    private LocalDate date;
    private double amount;
}
