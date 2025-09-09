package com.myjava.studentapplication.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Applications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course courseId;

    private double meritScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ApplicationStatus status;
   // private String status;


    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Student getStudentId() {
        return student;
    }

    public void setStudentId(Student studentId) {
        this.student = studentId;
    }

    public Course getCourseId() {
        return courseId;
    }

    public void setCourseId(Course courseId) {
        this.courseId = courseId;
    }

    public double getMeritScore() {
        return meritScore;
    }

    public void setMeritScore(double meritScore) {
        this.meritScore = meritScore;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }


}
