package com.myjava.studentapplication.Service;

import com.myjava.studentapplication.Entity.ApplicationStatus;
import com.myjava.studentapplication.Entity.Applications;
import com.myjava.studentapplication.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class meritCalculationService {

    @Autowired
    private CourseRepository courseRepository;

    public double calculateMeritScore(double percentage12th, double courseCutoff) {
        return (percentage12th >= courseCutoff) ? percentage12th : 0;  // Merit score based on cutoff
    }

    public void processApplication(Applications applications) {
        double meritScore = calculateMeritScore(applications.getStudentId().getMarks12th(), applications.getCourseId().getCourseCutoff());
        applications.setMeritScore(meritScore);

        if (meritScore >= applications.getCourseId().getCourseCutoff()) {
            applications.setStatus(ApplicationStatus.APPROVED);
        } else {
            applications.setStatus(ApplicationStatus.REJECTED);
        }
    }
}

