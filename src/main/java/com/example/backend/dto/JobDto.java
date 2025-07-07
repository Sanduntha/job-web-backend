package com.example.backend.dto;

import com.example.backend.entity.Application;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobDto {
    private Long id;
    private String title;
    private String description;
    private String companyName;
    private String location;
    private Long employerId;

//    @OneToMany(mappedBy = "job", cascade = CascadeType.REMOVE)
//    private List<Application> applications;

}
