package com.myjava.studentapplication.Repository;

import com.myjava.studentapplication.Entity.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByEmail(String email);
    Student findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE Student s SET s.coursePreference1 = NULL WHERE s.coursePreference1.id = :courseId")
    void unsetCoursePreference1(@Param("courseId") Long courseId);

    List<Student> findByCoursePreference1_courseId(Long courseId); // ✅ fixed

    @Modifying
    @Transactional
    @Query("UPDATE Student s SET s.coursePreference2 = NULL WHERE s.coursePreference2.id = :courseId")
    void unsetCoursePreference2(@Param("courseId") Long courseId);

    List<Student> findByCoursePreference2_courseId(Long courseId); // (Add this if needed)
}