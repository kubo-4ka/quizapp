package com.myapp.quizapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myapp.quizapp.model.Choice;
import com.myapp.quizapp.repositories.ChoiceRepository;

@Service
public class ChoiceServiceImpl implements ChoiceService {

    private final ChoiceRepository choiceRepository;

    @Autowired
    public ChoiceServiceImpl(ChoiceRepository choiceRepository) {
        this.choiceRepository = choiceRepository;
    }

    @Override
    public List<Choice> getChoicesByQuestionId(long questionId) {
        return choiceRepository.findByQuestionId(questionId);
    }

    @Override
    public void saveChoice(Choice choice) {
        choiceRepository.save(choice);
    }

    @Override
    public void deleteChoice(long id) {
        choiceRepository.deleteById(id);
    }
    @Override
    public int getQuestionIdByChoiceId(int choiceId) {
        Choice choice = choiceRepository.findById((long) choiceId).orElse(null);
        if (choice != null && choice.getQuestion() != null) {
            return choice.getQuestion().getId();
        }
        return -1; // エラー時の適切な処理を記述
    }
}
