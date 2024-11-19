package com.myapp.quizapp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/quiz/login")
    public String showLoginPage(@RequestParam(value = "themeId", required = false) Long themeId, Model model) {
        if (themeId != null) {
            model.addAttribute("themeId", themeId);
        }		Logger logger = LogManager.getLogger();
		logger.error("★ ★ ★LoginController getLoginPage Start");
        return "login"; // login画面へ
    }
}
