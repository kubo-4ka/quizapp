package com.myapp.quizapp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TitleController {
    @GetMapping("/quiz/title")
    public String titlePage() {
		Logger logger = LogManager.getLogger();
		logger.trace("Start");
        return "title"; // タイトル画面へのリダイレクト
    }

    @GetMapping("/")
    public String root() {
		Logger logger = LogManager.getLogger();
		logger.trace("Start");
        return "title"; // タイトル画面へのリダイレクト
    }
}

