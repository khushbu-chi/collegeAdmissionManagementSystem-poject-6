package com.myjava.studentapplication.Repository;

import com.myjava.studentapplication.Entity.ApplicationStatus;
import com.myjava.studentapplication.Entity.Applications;
import com.myjava.studentapplication.Entity.Course;
import com.myjava.studentapplication.Entity.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicationsRepository extends JpaRepository<Applications, Long> {
    List<Applications> findByStatus(ApplicationStatus status);

    @Modifying
    @Transactional
    @Query("DELETE FROM Applications a WHERE a.student.studentId = :studentId")
    void deleteByStudentId(Long studentId);
    List<Applications> findByStudent(Student student);
    @Modifying
    @Transactional
    @Query("DELETE FROM Applications a WHERE a.courseId.courseId = :courseId1")
    void deleteByCourseId(Long courseId1);
    List<Applications> findByCourseId(Course course);
}
