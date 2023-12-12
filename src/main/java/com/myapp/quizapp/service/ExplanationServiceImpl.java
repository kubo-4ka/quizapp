package com.myapp.quizapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myapp.quizapp.model.Explanation;
import com.myapp.quizapp.repositories.ExplanationRepository;

@Service
public class ExplanationServiceImpl implements ExplanationService {

    private final ExplanationRepository explanationRepository;

    @Autowired
    public ExplanationServiceImpl(ExplanationRepository explanationRepository) {
        this.explanationRepository = explanationRepository;
    }

    @Override
    public Explanation getExplanationByQuestionId(long questionId) {
        return explanationRepository.findByQuestionId(questionId);
    }

    @Override
    public void saveExplanation(Explanation explanation) {
        explanationRepository.save(explanation);
    }

    @Override
    public void deleteExplanation(long questionId) {
        explanationRepository.deleteByQuestionId(questionId);
    }

}
