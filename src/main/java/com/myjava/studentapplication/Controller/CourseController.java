package com.myjava.studentapplication.Controller;

import com.myjava.studentapplication.Entity.Course;
import com.myjava.studentapplication.Entity.Student;
import com.myjava.studentapplication.Repository.ApplicationsRepository;
import com.myjava.studentapplication.Repository.CourseRepository;
import com.myjava.studentapplication.Repository.StudentRepository;
import com.myjava.studentapplication.Service.CourseService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CourseController {

    @Autowired
    private final CourseRepository courseRepository;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ApplicationsRepository applicationsRepository;
    private StudentRepository studentRepository;

    public CourseController(CourseRepository courseRepository ) {
        this.courseRepository = courseRepository;
    }

    @GetMapping("/AddCourses")
    public String showCourseForm(Model model) {
        model.addAttribute("course", new Course());
        return "AddCourses"; // Thymeleaf template named register.html
    }
    @PostMapping("/AddCourses")
    public String addCourse(@Valid @ModelAttribute("course") Course course, BindingResult bindingResult) {
        if (courseRepository.existsByCourseName(course.getCourseName())) {
            bindingResult.rejectValue("courseName", "error.course", "Course already exists");
        }
        if (bindingResult.hasErrors()) {
            return "AddCourses"; // Show form with errors
        }
        courseRepository.save(course);
        return "redirect:/AllCourses";
    }
    @GetMapping("/AllCourses")
    public String showCourse(Model model) {
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
       // model.addAttribute("product", new Course());
        return "AllCourses"; // will render students.html
    }
    @GetMapping("/update-course/{id}")
    public String showUpdateCourse(@PathVariable Long id, Model model) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Course Id: " + id));
        model.addAttribute("course", course);
        return "update-course";
    }
    @PostMapping("/update-course")
    public String updateCourse(@ModelAttribute("course") Course course) {
        courseService.updateCourse(course);
        return "redirect:/AllCourses"; // go back to the list page
    }

    // ✅ Delete student
    @GetMapping("/deleteCourse/{id}")
    @Transactional
    public String deleteCourse(@PathVariable Long courseId) {
        applicationsRepository.deleteByCourseId(courseId);
        studentRepository.unsetCoursePreference1(courseId);       // Set preference1 = null
        studentRepository.unsetCoursePreference2(courseId);       // Set preference2 = null
        courseRepository.deleteById(courseId);
        return "redirect:/AllCourses";
    }
    @GetMapping("/CourseList")
    public String showCourses(Model model) {
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        // model.addAttribute("product", new Course());
        return "CourseList";
    }
}
