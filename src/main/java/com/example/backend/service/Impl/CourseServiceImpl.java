package com.example.backend.service.impl;

import com.example.backend.dto.CourseDto;
import com.example.backend.entity.Course;
import com.example.backend.entity.TrainerProfile;
import com.example.backend.repo.CourseRepo;
import com.example.backend.repo.TrainerProfileRepo;
import com.example.backend.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepo courseRepo;
    private final TrainerProfileRepo trainerRepo;
    private final ModelMapper modelMapper;

    @Override
    public CourseDto createCourse(CourseDto courseDto) throws Exception {
        TrainerProfile trainer = trainerRepo.findById(courseDto.getTrainerId())
                .orElseThrow(() -> new Exception("Trainer not found"));
        Course course = modelMapper.map(courseDto, Course.class);
        course.setTrainer(trainer);
        Course saved = courseRepo.save(course);
        return modelMapper.map(saved, CourseDto.class);
    }

    @Override
    public CourseDto updateCourse(CourseDto courseDto) throws Exception {
        Course course = courseRepo.findById(courseDto.getId())
                .orElseThrow(() -> new Exception("Course not found"));
        course.setTitle(courseDto.getTitle());
        course.setDuration(courseDto.getDuration());
        course.setDescription(courseDto.getDescription());
        course.setFee(courseDto.getFee());
        Course updated = courseRepo.save(course);
        return modelMapper.map(updated, CourseDto.class);
    }

    @Override
    public void deleteCourse(Long courseId) throws Exception {
        if (!courseRepo.existsById(courseId)) {
            throw new Exception("Course not found");
        }
        courseRepo.deleteById(courseId);
    }

    @Override
    public List<CourseDto> getCoursesByTrainer(Long trainerId) {
        return courseRepo.findByTrainerId(trainerId).stream()
                .map(course -> modelMapper.map(course, CourseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> searchCourses(String keyword) {
        return courseRepo.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword)
                .stream()
                .map(course -> modelMapper.map(course, CourseDto.class))
                .collect(Collectors.toList());
    }
}
