package com.myapp.quizapp.service;


import java.util.List;

import com.myapp.quizapp.model.Choice;

public interface ChoiceService {
	List<Choice> getChoicesByQuestionId(long questionId);
	void saveChoice(Choice choice);
	void deleteChoice(long id);
    // 選択肢IDから質問IDを取得
    int getQuestionIdByChoiceId(int choiceId);
}
