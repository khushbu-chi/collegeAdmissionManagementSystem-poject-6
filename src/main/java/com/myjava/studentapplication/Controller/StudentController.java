package com.myjava.studentapplication.Controller;

import com.myjava.studentapplication.Entity.ApplicationStatus;
import com.myjava.studentapplication.Entity.Applications;
import com.myjava.studentapplication.Entity.Course;
import com.myjava.studentapplication.Entity.Student;
import com.myjava.studentapplication.Repository.ApplicationsRepository;
import com.myjava.studentapplication.Repository.CourseRepository;
import com.myjava.studentapplication.Repository.StudentRepository;
import com.myjava.studentapplication.Service.ApplicationService;
import com.myjava.studentapplication.Service.CourseService;
import com.myjava.studentapplication.Service.StudentService;
import com.myjava.studentapplication.Service.meritCalculationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller

public class StudentController {

    //private StudentService studentService;
    @Autowired
    private ApplicationsRepository applicationsRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private meritCalculationService meritCalculationService;
    @Autowired
    private final StudentRepository studentRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;

    private ApplicationService applicationService;
    private ApplicationStatus status;

    public StudentController(StudentRepository  studentRepository, CourseService courseService) {
        this.studentRepository = studentRepository;
        this.courseService= courseService;
    }

    @GetMapping("/AddNewStudent")
        public String showAddForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("courses", courseService.getAllCourses());
        return "AddNewStudent"; // Thymeleaf template named register.html
    }
    @PostMapping("/AddNewStudent")
    public String addStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult,RedirectAttributes redirectAttributes,
                             @RequestParam Long coursePreference1,
                             @RequestParam Long coursePreference2) {
        Course course1 = courseRepository.findById(coursePreference1)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        Course course2 = courseRepository.findById(coursePreference2)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        student.setCoursePreference1(course1);
        student.setCoursePreference2(course2);
        if (studentRepository.existsByEmail(student.getEmail())) {
            bindingResult.rejectValue("email", "error.userForm", "Email already exists");
        }
        if (bindingResult.hasErrors()) {
            return "AddNewStudent"; // Show form with errors
        }

        studentRepository.save(student);
            // Create and save first application
            Applications application1 = new Applications();
            application1.setStudentId(student);
            application1.setCourseId(course1);
            meritCalculationService.processApplication(application1);
            applicationsRepository.save(application1);

            // Create and save second application
            Applications application2 = new Applications();
            application2.setStudentId(student);
            application2.setCourseId(course2);
            // Calculate merit & status
            meritCalculationService.processApplication(application2);
            // Save application
            applicationsRepository.save(application2);

            return "redirect:/AllStudents";
        }
    @GetMapping("/Admission-Form")
    public String showAdmissionForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("courses", courseService.getAllCourses());
        return "Admission-Form"; // Thymeleaf template named register.html
    }
    @PostMapping("/Admission-Form")
    public String addAdmission(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult, RedirectAttributes redirectAttributes,
                               @RequestParam Long coursePreference1,
                               @RequestParam Long coursePreference2) {
                                // Optional: hash password before saving
        Course course1 = courseRepository.findById(coursePreference1)
                                        .orElseThrow(() -> new RuntimeException("Course not found"));
        Course course2 = courseRepository.findById(coursePreference2)
                                        .orElseThrow(() -> new RuntimeException("Course not found"));
        student.setCoursePreference1(course1);
        student.setCoursePreference2(course2);
        if (studentRepository.existsByEmail(student.getEmail())) {
            bindingResult.rejectValue("email", "error.userForm", "Email already exists");
        }
        if (bindingResult.hasErrors()) {
            return "Admission-Form"; // Show form with errors
        }
            studentRepository.save(student);
            // Create and save first application
            Applications application1 = new Applications();
            application1.setStudentId(student);
            application1.setCourseId(course1);
            meritCalculationService.processApplication(application1);
            applicationsRepository.save(application1);

            // Create and save second application
            Applications application2 = new Applications();
            application2.setStudentId(student);
            application2.setCourseId(course2);
            // Calculate merit & status
            meritCalculationService.processApplication(application2);
            // Save application
            applicationsRepository.save(application2);
        // Add flash message
        redirectAttributes.addFlashAttribute("successMessage", "Form submitted successfully!");

        return "redirect:/success";
    }
    @GetMapping("/AllStudents")
    public String showStudents(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "AllStudents"; // will render students.html
    }
    @GetMapping("/update-student/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id: " + id));
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("student", student);
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("statuses", ApplicationStatus.values());
        return "update-student";
    }
    @PostMapping("/update-student")
    public String updateStudent(@Valid @ModelAttribute("student") Student student,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
                                @RequestParam Long coursePreference1,
                                @RequestParam Long coursePreference2) {
            // Check if the email is used by another student
            Optional<Student> existingStudent = Optional.ofNullable(studentRepository.findByEmail(student.getEmail()));
            if (existingStudent.isPresent() && !existingStudent.get().getStudentId().equals(student.getStudentId())) {
                bindingResult.rejectValue("email", "error.student", "❌ Email is already in use by another student.");
            }
            // Validate form
            if (bindingResult.hasErrors()) {
                return "Update-Student"; // Show form again with errors
            }

            // Get selected courses
            Course course1 = courseRepository.findById(coursePreference1)
                    .orElseThrow(() -> new RuntimeException("Course 1 not found"));
            Course course2 = courseRepository.findById(coursePreference2)
                    .orElseThrow(() -> new RuntimeException("Course 2 not found"));

            // Set preferences
            student.setCoursePreference1(course1);
            student.setCoursePreference2(course2);

            // Save student
            studentRepository.save(student);

            // Update applications
            List<Applications> applications = applicationsRepository.findByStudent(student);
            for (Applications app : applications) {
                if (app.getCourseId().getCourseId().equals(course1.getCourseId()) || app.getCourseId().getCourseId().equals(course2.getCourseId())) {
                    meritCalculationService.processApplication(app); // Recalculate merit
                    applicationsRepository.save(app);
                }
            }
            redirectAttributes.addFlashAttribute("successMessage", "✅ Student details updated successfully!");
            return "redirect:/AllStudents"; // go back to the list page
        }
    // ✅ Delete student
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        applicationsRepository.deleteByStudentId(id);
        studentRepository.deleteById(id);
        return "redirect:/AllStudents";
    }
    @GetMapping("/success")
    public String showSuccessPage() {
        return "success"; // Thymeleaf template: success.html
    }
}
