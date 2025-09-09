package com.myjava.studentapplication.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jdk.jfr.Name;

@Entity

public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;


    @Column(unique = true)
    @NotBlank(message = "Course name is required")
    private String courseName;

    @Column(nullable = false)
    private int courseDuration;

    @Column(nullable = false)
    private Double courseCutoff;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(int courseDuration) {
        this.courseDuration = courseDuration;
    }

    public Double getCourseCutoff() {
        return courseCutoff;
    }

    public void setCourseCutoff(Double courseCutoff) {
        this.courseCutoff = courseCutoff;
    }
}
