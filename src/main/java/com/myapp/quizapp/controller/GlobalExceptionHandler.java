package com.myapp.quizapp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
		Logger logger = LogManager.getLogger();
		logger.error("★★★GlobalExceptionHandler★★★", ex);
		System.out.print(ex);
        // エラーログの記録などの処理を行う
        // カスタムエラーメッセージを表示する ModelAndView を構築する
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", "画面遷移中にエラーが発生しました。");
        return modelAndView;
    }
}
