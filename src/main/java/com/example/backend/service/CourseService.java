package com.example.backend.service;

import com.example.backend.dto.CourseDto;

import java.util.List;

public interface CourseService {
    CourseDto createCourse(CourseDto courseDto) throws Exception;
    CourseDto updateCourse(CourseDto courseDto) throws Exception;
    void deleteCourse(Long courseId) throws Exception;
    List<CourseDto> getCoursesByTrainer(Long trainerId);
    List<CourseDto> searchCourses(String keyword);
}
