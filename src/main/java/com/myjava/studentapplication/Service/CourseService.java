package com.myjava.studentapplication.Service;

import com.myjava.studentapplication.Entity.Course;
import com.myjava.studentapplication.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    public void updateCourse(Course course) {
        courseRepository.save(course); // works for both create and update
    }
}
