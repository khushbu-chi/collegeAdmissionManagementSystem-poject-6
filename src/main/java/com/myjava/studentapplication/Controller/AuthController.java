package com.myjava.studentapplication.Controller;
import com.myjava.studentapplication.Entity.Student;
import com.myjava.studentapplication.Entity.User;
import com.myjava.studentapplication.Repository.StudentRepository;
import com.myjava.studentapplication.Repository.UserRepository;
import com.myjava.studentapplication.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // will load login.html
    }
    @GetMapping("/adminhome")
    public String adminDashboard(Model model) {
        List<Student> students = studentService.getAllStudents();
            model.addAttribute("students", students);
        return "adminhome"; // View name
    }

    @GetMapping("/userhome")
    public String showUserHome(Model model, Principal principal) {
        String username = principal.getName(); // Logged-in username

        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            String name = STR."\{userOpt.get().getFirstName()} \{userOpt.get().getLastName()}";
            model.addAttribute("name", name);
        } else {
            model.addAttribute("name", "User"); // fallback
        }

        return "userhome"; // your HTML/JSP page name
    }
    @GetMapping("/userprofile")
    public String showUserprofile(Model model, Principal principal) {
        String username = principal.getName();
        Optional<User> userOpt = userRepository.findByUsername(username);
        if(userOpt.isPresent()) {
            String email = userOpt.get().getEmail();
            Student student = studentRepository.findByEmail(email);
            if (student != null) {
                model.addAttribute("student", student); // Pass full student object
                return "userprofile";
            } else {
                // Optional: Redirect to error or login page
                return "redirect:/error"; // or show a message
            }
        }// From logged-in user
        return "userprofile";
    }
}