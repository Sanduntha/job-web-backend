package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobSeekerDto {
    private Long id;
    private String name;
    private String jobCategory;
    private String skill;
    private String contactNumber;
    private Long userId;
}
