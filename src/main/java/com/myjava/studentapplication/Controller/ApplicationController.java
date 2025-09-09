package com.myjava.studentapplication.Controller;

import com.myjava.studentapplication.Entity.ApplicationStatus;
import com.myjava.studentapplication.Entity.Applications;
import com.myjava.studentapplication.Repository.ApplicationsRepository;
import com.myjava.studentapplication.Repository.CourseRepository;
import com.myjava.studentapplication.Repository.StudentRepository;
import com.myjava.studentapplication.Service.ApplicationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.io.IOException;
import java.util.List;

@Controller
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ApplicationsRepository applicationRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/approve/{applicationId}")
    public String approveApplication(@PathVariable Long applicationId) {
        applicationService.approveApplication(applicationId);
        return "redirect:/AllApplications";

    }

    @GetMapping("/reject/{applicationId}")
    public String rejectApplication(@PathVariable Long applicationId) {
        applicationService.rejectApplication(applicationId);
        return "redirect:/AllApplications";
    }

    @GetMapping("/AllApplications")
    public String viewApplications(Model model) {
        List<Applications> applications = applicationService.getAllapplications();
        model.addAttribute("applications", applications);
        return "AllApplications";  // Return page to list applications
    }
    @GetMapping("/AdmissionList")
    public String viewApprovedApplications(Model model) {
        List<Applications> approvedApplications = applicationService.getApprovedApplications();
       // model.addAttribute("statuses", ApplicationStatus.values());
        model.addAttribute("applications", approvedApplications);
        return "AdmissionList";
    }

    @GetMapping("/export/csv")
    public void exportCSV(HttpServletResponse response) throws IOException {
        List<Applications> applications = applicationService.getApprovedApplications();
        applicationService.exportToCSV(response, applications);
    }

}