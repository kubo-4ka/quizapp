package com.myapp.quizapp.service;

import com.myapp.quizapp.model.Explanation;

public interface ExplanationService {
    Explanation getExplanationByQuestionId(long questionId);
    void saveExplanation(Explanation explanation);
    void deleteExplanation(long questionId);
}
