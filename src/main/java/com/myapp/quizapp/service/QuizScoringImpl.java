package com.myapp.quizapp.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.myapp.quizapp.model.Question;

@Service
public class QuizScoringImpl implements QuizScoring {

    @Override
    public double scoreQuiz(List<Question> randomQuestions, List<Integer> selectedChoiceIds) {
		Logger logger = LogManager.getLogger();
		logger.error("★★★scoreQuiz Start");
        double score = 0.0;

        // 質問リストと回答IDリストのサイズが同じであることを確認
		logger.error("★★randomQuestions.size() : " + randomQuestions.size());
		logger.error("★★selectedChoiceIds.size() : " + selectedChoiceIds.size());
        if (randomQuestions.size() == selectedChoiceIds.size()) {
            // 各質問について正解判定を行い、スコアを計算
            for (int i = 0; i < randomQuestions.size(); i++) {
                Question question = randomQuestions.get(i);
                int correctAnswerId = question.getCorrectAnswerId();
        		logger.error("★★★★selectedChoiceIds.get(i) : " + selectedChoiceIds.get(i));
        		logger.error("★★★★correctAnswerId : " + correctAnswerId);
                
                // ユーザーの回答が正解であればスコアを増加
                if (selectedChoiceIds.get(i) == correctAnswerId) {
                    score += 1.0;
                }
            }
        } else {
            // サイズが異なる場合はエラーとして処理するか、例外を投げるなどの対応が必要
            // ここでは例外を投げる例を示します
            throw new IllegalArgumentException("質問リストと回答IDリストのサイズが一致しません。");
        }

        return score;
    }
}
