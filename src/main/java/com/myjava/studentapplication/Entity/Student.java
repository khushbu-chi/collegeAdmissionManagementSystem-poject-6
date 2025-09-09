package com.myjava.studentapplication.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    @Column(nullable = false)
    private String firstName;
    private String lastName;

    @Email(message = "Email ID Already Exists")
    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private double marks12th;

    @Column(nullable = false)
    private double percentage12th;

    @ManyToOne
    @JoinColumn(name = "coursepreference1", foreignKey = @ForeignKey(name = "FK_course_pref1"))
    private Course coursePreference1;

    @ManyToOne
    @JoinColumn(name = "coursepreference2", foreignKey = @ForeignKey(name = "FK_course_pref2"))
    private Course coursePreference2;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus = ApplicationStatus.PENDING;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Applications> applications = new ArrayList<>();

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getMarks12th() {
        return marks12th;
    }

    public void setMarks12th(double marks12th) {
        this.marks12th = marks12th;
    }

    public double getPercentage12th() {
        return percentage12th;
    }

    public void setPercentage12th(double percentage12th) {
        this.percentage12th = percentage12th;
    }

    public Course getCoursePreference1() {
        return coursePreference1;
    }

    public void setCoursePreference1(Course coursePreference1) {
        this.coursePreference1 = coursePreference1;
    }

    public Course getCoursePreference2() {
        return coursePreference2;
    }

    public void setCoursePreference2(Course coursePreference2) {
        this.coursePreference2 = coursePreference2;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public Student orElseThrow(Object studentNotFound) {
        return null;
    }
    public List<Applications> getApplications() {
        if (applications == null) {
            applications = new ArrayList<>();
        }
        return applications;
    }

    //
    //
    // Getters and Setters

}


