package com.myapp.quizapp.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        // エラー情報を取得し、カスタムエラーページに渡す処理を追加
        return "error";
    }

//    @Override
//    public String getErrorPath() {
//        return "/error";
//    }
}
