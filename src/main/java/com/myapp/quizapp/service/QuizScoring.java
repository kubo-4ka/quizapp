package com.myapp.quizapp.service;

import java.util.List;

import com.myapp.quizapp.model.Question;

public interface QuizScoring {
    double scoreQuiz(List<Question> randomQuestions, List<Integer> selectedChoiceIds);
    boolean isCorrectAnswer(Question question, Integer selectedChoiceId);
    boolean isCorrectAnswer(Question question, List<Integer> selectedChoiceIds);
}

