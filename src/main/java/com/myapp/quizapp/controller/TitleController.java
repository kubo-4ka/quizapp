package com.myapp.quizapp.controller;

import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

		// BCryptテスト
		int length = 12;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!#$%&()=-\\@+*/<>?:;,._";
        Random random = new Random();
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            randomString.append(characters.charAt(index));
        }
        String rawPassword = randomString.toString(); // 生パスワード
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(rawPassword);
		logger.error("★raw ; " + rawPassword);
		logger.error("★hashed ; " + hashedPassword);

		return "title"; // タイトル画面へ
    }
}
