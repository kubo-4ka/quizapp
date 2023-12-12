package com.myapp.quizapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {
    @GetMapping("/common/header")
    public String header() {
        return "common/header";
    }

    @GetMapping("/common/footer")
    public String footer() {
        return "common/footer";
    }
}

