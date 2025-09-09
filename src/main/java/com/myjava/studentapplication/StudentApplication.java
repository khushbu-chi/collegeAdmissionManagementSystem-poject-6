package com.myjava.studentapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class StudentApplication {
    //@GetMapping("/index")
    public static void main(String[] args) {
        SpringApplication.run(StudentApplication.class, args);

    }



}
