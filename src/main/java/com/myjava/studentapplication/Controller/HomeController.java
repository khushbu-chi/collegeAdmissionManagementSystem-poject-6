package com.myjava.studentapplication.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/index")
    public String homePage() {
        return "index"; // renders templates/index.html
    }
}
