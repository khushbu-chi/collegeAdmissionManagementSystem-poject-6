package com.myjava.studentapplication.Repository;

import com.myjava.studentapplication.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByCourseName(String courseName);
    boolean existsByCourseName(String courseName);

}
