package com.myjava.studentapplication.Controller;

import com.myjava.studentapplication.Entity.Applications;
import com.myjava.studentapplication.Entity.User;
import com.myjava.studentapplication.Repository.ApplicationsRepository;
import com.myjava.studentapplication.Repository.StudentRepository;
import com.myjava.studentapplication.Repository.UserRepository;
import com.myjava.studentapplication.Service.ApplicationService;
import com.myjava.studentapplication.Service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Set;
@Controller
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    @Autowired
    private ApplicationsRepository applicationRepository;
    @Autowired
    private StudentService studentService;
    private ApplicationService applicationService;
    private Object Student;
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder,StudentRepository studentRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.studentRepository=studentRepository;
    }
    @GetMapping("/register")
    public String showSingUpForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // Thymeleaf template named register.html
    }
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {

        // Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            bindingResult.rejectValue("email", "error.user", "Email already exists");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            bindingResult.rejectValue("username", "error.user", "Username already exists");
        }

        if (bindingResult.hasErrors()) {
            return "register";  // Return form with error messages
        }


        // Set default role if none provided
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Set.of("USER"));
        }

        // Hash the password and save the user
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
        return "login";
    }

    @GetMapping("/alladmissions")
    public String viewadmissions(Model model) {
        List<Applications> approvedApplications = applicationRepository.findAll();
        // model.addAttribute("statuses", ApplicationStatus.values());
        model.addAttribute("applications", approvedApplications);
        return "alladmissions";
    }

}
