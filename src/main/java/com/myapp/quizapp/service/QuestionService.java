package com.myapp.quizapp.service;

import java.util.List;

import com.myapp.quizapp.model.Question;

public interface QuestionService {
    List<Question> getAllQuestions();
    Question getQuestionById(long id);
    void saveQuestion(Question question);
    void deleteQuestion(long id);
    public Long getTotalQuestionCount();
	List<Question> getRandomQuestions(int count);
	List<Question> getAllRandomQuestions();

}
