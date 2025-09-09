package com.myjava.studentapplication.Service;

import com.myjava.studentapplication.Entity.ApplicationStatus;
import com.myjava.studentapplication.Entity.Applications;
import com.myjava.studentapplication.Entity.Student;
import com.myjava.studentapplication.Repository.ApplicationsRepository;
import com.myjava.studentapplication.Repository.StudentRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Service
public class ApplicationService {
    @Autowired
    private ApplicationsRepository applicationRepository;
    @Autowired
    private StudentRepository studentRepository;

    public List<Applications> getAllapplications() {
        return applicationRepository.findAll();
    }
    public void approveApplication(Long applicationId) {
        Applications application = applicationRepository.findById(applicationId).orElseThrow();
        application.setStatus(ApplicationStatus.APPROVED);
        applicationRepository.save(application);
        Student student = application.getStudentId();
        student.setApplicationStatus(ApplicationStatus.APPROVED);
        studentRepository.save(student);
    }

    public void rejectApplication(Long applicationId) {
        Applications application = applicationRepository.findById(applicationId).orElseThrow();
        application.setStatus(ApplicationStatus.REJECTED);
        applicationRepository.save(application);

        Student student = application.getStudentId();
        student.setApplicationStatus(ApplicationStatus.REJECTED);
        studentRepository.save(student);
    }
    public List<Applications> getApprovedApplications() {
        return applicationRepository.findByStatus(ApplicationStatus.valueOf("APPROVED"));
    }
    public void exportToCSV(HttpServletResponse response, List<Applications> applications) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=applications.csv");

        try (PrintWriter writer = response.getWriter();
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("ID", "Student Name","Course Name","Status"))) {

            for (Applications app : applications) {
                csvPrinter.printRecord(app.getApplicationId(), app.getStudentId().getFirstName()+app.getStudentId().getLastName(),app.getCourseId().getCourseName() ,app.getStatus().toString());
            }

            csvPrinter.flush();
        }
    }

}
