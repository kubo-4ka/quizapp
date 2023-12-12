package com.myapp.quizapp.service;

import java.util.List;

import com.myapp.quizapp.model.Question;

public interface QuizScoring {
    double scoreQuiz(List<Question> randomQuestions, List<Integer> selectedChoiceIds);
}

