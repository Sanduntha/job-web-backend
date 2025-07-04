package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainerDto {
    private Long id;
    private String name;
    private String courseCategory;
    private String contactNumber;
    private String experience;
    private String qualification;
    private Long userId;
}
