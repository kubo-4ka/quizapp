package com.myapp.quizapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerStatus {
    private int questionNumber; // 質問番号
    private String questionText; // 質問内容
    private String correctOrIncorrect; // 正誤

    public AnswerStatus(int questionNumber, String questionText, String correctOrIncorrect) {
        this.questionNumber = questionNumber;
        this.questionText = questionText;
        this.correctOrIncorrect = correctOrIncorrect;
    }
}