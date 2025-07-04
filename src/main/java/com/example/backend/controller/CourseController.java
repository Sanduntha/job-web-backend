package com.example.backend.controller;

import com.example.backend.dto.CourseDto;
import com.example.backend.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@CrossOrigin
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody CourseDto courseDto) {
        try {
            CourseDto created = courseService.createCourse(courseDto);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateCourse(@RequestBody CourseDto courseDto) {
        try {
            CourseDto updated = courseService.updateCourse(courseDto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long courseId) {
        try {
            courseService.deleteCourse(courseId);
            return ResponseEntity.ok("Course deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/trainer/{trainerId}")
    public ResponseEntity<List<CourseDto>> getCoursesByTrainer(@PathVariable Long trainerId) {
        List<CourseDto> courses = courseService.getCoursesByTrainer(trainerId);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CourseDto>> searchCourses(@RequestParam String keyword) {
        List<CourseDto> courses = courseService.searchCourses(keyword);
        return ResponseEntity.ok(courses);
    }
}
