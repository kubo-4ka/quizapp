package com.myapp.quizapp.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.myapp.quizapp.model.Theme;
import com.myapp.quizapp.service.ThemeService;

@Controller
public class TitleController {

    @Autowired
    private ThemeService themeService;

    @GetMapping("/")
    public String root(Model model) {
        List<Theme> themes = themeService.getAllThemes();
        model.addAttribute("themes", themes);
		Logger logger = LogManager.getLogger();
		logger.error("★ ★ ★TitleController root Start");
        return "title"; // タイトル画面へ
    }

    @GetMapping("/quiz/title")
    public String getQuizTitlePage(Model model) {
        List<Theme> themes = themeService.getAllThemes();
        model.addAttribute("themes", themes);
		Logger logger = LogManager.getLogger();
		logger.error("★ ★ ★TitleController getQuizTitlePage Start");
        return "title"; // タイトル画面へ
    }
}
