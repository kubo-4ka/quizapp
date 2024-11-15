package com.myapp.quizapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myapp.quizapp.model.Choice;
import com.myapp.quizapp.model.QuestionType;
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
    // QuestionTypeに応じた選択肢チェックロジック
    public boolean isMultipleChoice(QuestionType questionType) {
        return questionType == QuestionType.MULTIPLE_CHOICE;
    }

    // Questionに基づいて選択肢の処理を調整
    public void adjustChoiceLogicForQuestionType(long questionId, List<Integer> selectedChoiceIds, QuestionType questionType) {
        if (isMultipleChoice(questionType)) {
            // 複数選択の場合の処理
            // selectedChoiceIds には複数の選択肢IDが含まれる
        } else {
            // 単一選択の場合の処理
            // selectedChoiceIds には1つの選択肢IDが含まれる
        }
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
