package com.myjava.studentapplication.Service;


import com.myjava.studentapplication.Entity.Student;
import com.myjava.studentapplication.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void updateStudent(Student student) {
        studentRepository.save(student);
    }


    public boolean emailExists(String email) {
        return studentRepository.existsByEmail(email);
    }

    public String registerStudent(Student student) {
        if (emailExists(student.getEmail())) {
            return "Email already exists!";
        }
        studentRepository.save(student);
        return "User registered successfully!";

    }

}